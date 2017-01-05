package com.forsenboyz.rise42.coop.network;


import com.badlogic.gdx.utils.TimeUtils;
import com.forsenboyz.rise42.coop.states.StateManager;

import java.util.Queue;


public class MessageManager {

    private final String INIT_CODE = "0";
    private final String PAUSE_CODE = "1";
    private final String PLAY_CODE = "2";
    private final String MOVE_CODE = "3";
    private final String ROTATE_CODE = "4";
    private final String ANIMATION_CODE = "5";

    private final static int OUTPUT_WAIT = 50;

    private Connection connection;
    private StateManager stateManager;

    private Thread inputThread;
    private Thread outputThread;

    public MessageManager(String host, int port, StateManager stateManager) {
        this.stateManager = stateManager;
        this.connection = new Connection(host, port);
    }

    public void connect() {
        if (!connection.isConnected()) {
            if (this.inputThread != null) {
                this.inputThread.interrupt();
            }

            this.connection.connect();
            this.startInputThread();
            this.startOutputThread();
        }
    }

    public void pause() {
        this.connection.sendMessage(PAUSE_CODE);
    }

    public void play() {
        this.connection.sendMessage(PLAY_CODE);
    }

    public void move(boolean forward) {
        this.connection.sendMessage(
                MOVE_CODE + ":"+Parameters.FOR+"(" + (forward ? "1" : "0") + ")" +
                        ":"+Parameters.ANG+"("+this.stateManager.getPlayState().updateRotation()+")"
        );
    }

    public void rotate(int angle) {
        this.connection.sendMessage(ROTATE_CODE + ":"+Parameters.ANG+"(" + angle + ")");
    }

    public void animation(int num, int angle) {
        this.connection.sendMessage(ANIMATION_CODE+":"+Parameters.N+"("+num+"):"+Parameters.ANG+"(" + angle + ")");
    }

    private void startInputThread() {
        inputThread = new Thread(
                () -> {
                    Queue<String> incomes;

                    while (this.connection.isConnected() && !inputThread.isInterrupted()) {
                        incomes = this.connection.getIncomeMessages();

                        while (incomes != null && !incomes.isEmpty()) {
                            Message msg = new Message(incomes.poll());
                            System.out.println("!!!!!!!!!!!!!!!!!!" + msg.toString());

                            switch (msg.getCode()) {

                                case INIT_CODE:
                                    int variant = msg.getParams().get(Parameters.VAR).intValue();
                                    this.stateManager.getPlayState().setInitialParameters(variant);
                                    break;

                                case PAUSE_CODE:
                                    this.stateManager.pause();
                                    break;

                                case PLAY_CODE:
                                    this.stateManager.play();
                                    break;

                                case MOVE_CODE:
                                    float x = msg.getParams().get(Parameters.X);
                                    float y = msg.getParams().get(Parameters.Y);

                                    if (msg.isResponse()) {
                                        this.stateManager.getPlayState().moveHero(x, y);
                                    } else {
                                        this.stateManager.getPlayState().moveAnotherHero(x, y);
                                    }
                                    break;

                                case ROTATE_CODE:
                                    int angle = msg.getParams().get(Parameters.ANG).intValue();
                                    if (msg.isResponse()) {
                                        this.stateManager.getPlayState().rotateHero(angle);
                                    } else {
                                        this.stateManager.getPlayState().rotateAnotherHero(angle);
                                    }
                                    break;
                            }
                        }

                    }

                });
        inputThread.setDaemon(true);
        inputThread.start();
    }

    /**
     * Starts a thread, with hero rotation auto-commit
     */
    private void startOutputThread() {
        outputThread = new Thread(
                () -> {
                    while (this.connection.isConnected()) {

                        while (stateManager.getPlayState().isActive()) {
                            if (TimeUtils.timeSinceMillis(connection.getLastOutputTime()) > OUTPUT_WAIT) {
                                if(stateManager.getPlayState().hasRotated()){
                                    this.rotate(stateManager.getPlayState().updateRotation());
                                }
                            }
                        }

                        try {
                            synchronized (stateManager.getPlayState()) {
                                stateManager.getPlayState().wait();
                            }
                        } catch(InterruptedException interEx){
                            interEx.printStackTrace();
                        }
                    }
                }
        );
        outputThread.setDaemon(true);
        outputThread.start();
    }
}
