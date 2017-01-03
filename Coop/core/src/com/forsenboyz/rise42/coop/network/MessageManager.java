package com.forsenboyz.rise42.coop.network;


import com.forsenboyz.rise42.coop.states.StateManager;

import java.util.Queue;


public class MessageManager {

    private final String INIT_CODE = "0";
    private final String PAUSE_CODE = "1";
    private final String PLAY_CODE = "2";
    private final String MOVE_CODE = "3";
    private final String ROTATE_CODE = "4";

    private Connection connection;
    private StateManager stateManager;

    Thread handlingThread;

    public MessageManager(String host, int port, StateManager stateManager) {
        this.stateManager = stateManager;
        this.connection = new Connection(host, port);
    }

    public void connect() {
        if (!connection.isConnected()) {
            if (this.handlingThread != null) {
                this.handlingThread.interrupt();
            }

            this.connection.connect();
            this.startHandlingThread();
        }
    }

    public void pause() {
        this.connection.sendMessage(PAUSE_CODE);
    }

    public void play() {
        this.connection.sendMessage(PLAY_CODE);
    }

    public void move(boolean forward) {
        this.connection.sendMessage(MOVE_CODE + ":for(" + (forward ? "1" : "0") + ")");
    }

    public void rotate(int angle) {
        this.connection.sendMessage(ROTATE_CODE + ":clk(" + angle + ")");
    }

    private void startHandlingThread() {
        handlingThread = new Thread(() -> {
            Queue<String> incomes;

            while (this.connection.isConnected() && !handlingThread.isInterrupted()) {
                incomes = this.connection.getIncomeMessages();

                while (incomes != null && !incomes.isEmpty()) {
                    Message msg = new Message(incomes.poll());
                    System.out.println("!!!!!!!!!!!!!!!!!!" + msg.toString());

                    switch (msg.getCode()) {

                        case INIT_CODE:
                            int variant = msg.getParams().get("var").intValue();
                            this.stateManager.getPlayState().setInitialParameters(variant);
                            break;

                        case PAUSE_CODE:
                            this.stateManager.pause();
                            break;

                        case PLAY_CODE:
                            this.stateManager.play();
                            break;

                        case MOVE_CODE:
                            float x = msg.getParams().get("x");
                            float y = msg.getParams().get("y");

                            if (msg.isResponse()) {
                                this.stateManager.getPlayState().moveHero(x, y);
                            } else {
                                this.stateManager.getPlayState().moveAnotherHero(x, y);
                            }
                            //break;    WOW, it's probably cool

                        case ROTATE_CODE:
                            int angle = msg.getParams().get("ang").intValue();
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
        handlingThread.setDaemon(true);
        handlingThread.start();
    }
}
