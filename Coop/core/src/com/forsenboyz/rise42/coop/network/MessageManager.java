package com.forsenboyz.rise42.coop.network;


import com.badlogic.gdx.utils.TimeUtils;
import com.forsenboyz.rise42.coop.states.StateManager;
import static com.forsenboyz.rise42.coop.network.Parameters.*;


public class MessageManager {

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
                MOVE_CODE + ":"+FOR+"(" + (forward ? "1" : "0") + ")" +
                        ":"+ANG+"("+this.stateManager.getPlayState().updateRotation()+")"
        );
    }

    public void rotate(int angle) {
        this.connection.sendMessage(ROTATE_CODE + ":"+ANG+"(" + angle + ")");
    }

    public void animation(int index, int angle) {
        this.connection.sendMessage(ANIMATION_CODE+":"+IND+"("+index+"):"+ANG+"(" + angle + ")");
    }

    private void startInputThread() {
        inputThread = new Thread(
                new InputHandlingRunnable(connection,stateManager)
        );
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
