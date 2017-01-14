package com.forsenboyz.rise42.coop.network;


import com.badlogic.gdx.utils.TimeUtils;
import com.forsenboyz.rise42.coop.states.StateManager;


public class MessageManager {

    private final static int OUTPUT_WAIT = 50;

    private static final String FOR="FOR";
    private static final String ANG="ANG";
    private static final String IND = "IND";

    private static final String PAUSE_CODE = "1";
    private static final String PLAY_CODE = "2";
    private static final String MOVE_CODE = "3";
    private static final String ROTATE_CODE = "4";
    private static final String ANIMATION_CODE = "5";

    private Connection connection;
    private StateManager stateManager;

    private Thread outputThread;

    public MessageManager(String host, int port, StateManager stateManager) {
        this.stateManager = stateManager;
        this.connection = new Connection(host, port, new InputMessageHandler(stateManager));
    }

    public void connect() {
        if (!connection.isConnected()) {
            this.connection.connect();
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

    public void action(int index, int angle) {
        this.connection.sendMessage(ANIMATION_CODE+":"+IND+"("+index+"):"+ANG+"(" + angle + ")");
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
