package com.forsenboyz.rise42.coop.network;


import com.badlogic.gdx.Gdx;
import com.forsenboyz.rise42.coop.states.StateManager;

import java.util.Queue;


public class MessageManager {

    private final int OK_CODE = 0;

    private final String PAUSE_CODE = "1";
    private final String PLAY_CODE = "2";
    private final String MOVE_CODE = "3";

    private Connection connection;
    private StateManager stateManager;

    public MessageManager(String host, int port, StateManager stateManager) {
        this.stateManager = stateManager;
        this.connection = new Connection(host, port);

        this.connection.connect();

        this.startHandlingThread();
    }

    public void pause() {
        this.connection.sendMessage(PAUSE_CODE);
    }

    public void play() {
        this.connection.sendMessage(PLAY_CODE);
    }

    private void startHandlingThread() {
        new Thread(() -> {
            Queue<String> incomes;

            while (this.connection.isConnected()) {
                incomes = this.connection.getIncomeMessages();

                while (incomes != null && !incomes.isEmpty()) {
                    String s = incomes.poll();
                    System.out.println("!!!!!!!!!!!!!!!!!!" + s);
                    Gdx.app.postRunnable(() -> {
                        switch (s.substring(1,2)) {
                            case PAUSE_CODE:
                                this.stateManager.pause();
                                break;
                            case PLAY_CODE:
                                this.stateManager.play();
                                break;
                            case MOVE_CODE:
                                //
                                break;
                        }
                    });
                }

            }

        }).start();
    }
}
