package com.forsenboyz.rise42.coop.network;


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
    }

    public void pause() {
        this.connection.sendMessage(PAUSE_CODE);
    }

    public void play() {
        this.connection.sendMessage(PLAY_CODE);
    }

    private void handleUpdates() {
        new Thread(() -> {
            Queue<String> incomes;
            String s;

            while (this.connection.isConnected()) {
                incomes = this.connection.getIncomeMessages();

                while (!incomes.isEmpty()) {
                    s = incomes.poll().substring(1, 3);
                    System.out.println("!!!!!!!!!!!!!!!!!!" + s);
                    switch (s) {
                        case PAUSE_CODE:
                            this.stateManager.pause();
                            break;
                        case PLAY_CODE:
                            this.stateManager.play();
                            break;
                    }
                }
            }
        }).start();
    }
}
