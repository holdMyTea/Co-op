package com.forsenboyz.rise42.coop.network;


import com.badlogic.gdx.Gdx;
import com.forsenboyz.rise42.coop.states.StateManager;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Queue;


public class MessageManager {

    private final String INIT_CODE = "0";
    private final String PAUSE_CODE = "1";
    private final String PLAY_CODE = "2";
    private final String MOVE_CODE = "3";
    private final String ROTATE_CODE = "4";

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

    public void move(boolean forward) {
        this.connection.sendMessage(MOVE_CODE+":for("+ (forward ? "1" : "0")+")");
    }

    public void rotate(boolean clockwise) {
        this.connection.sendMessage(ROTATE_CODE+":clk("+ (clockwise ? "1" : "0")+")");
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
                        Message msg = new Message(s);
                        switch (msg.getCode()) {

                            case INIT_CODE:
                                int variant = (int) msg.getParams().get("var").floatValue();
                                this.stateManager.getPlayState().setInitialParameters(variant);
                                break;

                            case PAUSE_CODE:
                                this.stateManager.pause();
                                break;

                            case PLAY_CODE:
                                this.stateManager.play();
                                break;

                            case MOVE_CODE:
                                int position = Integer.parseInt(s.substring(s.indexOf("(")+1,s.lastIndexOf(")")));
                                if(s.substring(0,1).equals("r")){
                                    this.stateManager.getPlayState().moveHero(0,0);
                                } else if(s.substring(0,1).equals("s")){
                                    this.stateManager.getPlayState().moveAnotherHero(0,0);
                                }
                                break;

                            case ROTATE_CODE:
                                int angle = msg.getParams().get("ang").intValue();
                                if(msg.isResponse()){
                                    this.stateManager.getPlayState().rotateHero(angle);
                                } else{
                                    this.stateManager.getPlayState().rotateAnotherHero(angle);
                                }
                                break;
                        }
                    });
                }

            }

        }).start();
    }
}
