package com.forsenboyz.rise42.coop.network;

import com.forsenboyz.rise42.coop.states.StateManager;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Queue;

class InputHandlingRunnable implements Runnable {

    private static final String INIT = "init";
    private static final String PLAY = "play";
    private static final String PAUSE = "pause";
    private static final String HEROES = "heroes";

    private static final String MAGE = "mage";
    private static final String WAR = "war";

    private static final String X = "x";
    private static final String Y = "y";
    private static final String ANGLE = "a";
    private static final String ACTIONS = "act";


    private Connection connection;
    private StateManager stateManager;

    InputHandlingRunnable(Connection connection, StateManager stateManager) {
        this.connection = connection;
        this.stateManager = stateManager;
    }

    @Override
    public void run() {
        System.out.println("Input runnable started");
        Queue<String> incomes;

        while (this.connection.isConnected()) {
            incomes = this.connection.getIncomeMessages();

            while (incomes != null && !incomes.isEmpty()) {
                System.out.println(incomes.peek());
                JsonObject msg = new JsonParser().parse(incomes.poll()).getAsJsonObject();

                if(msg.has(INIT)){
                    this.stateManager.getPlayState().setInitialParameters(msg.get(INIT).getAsInt());
                }

                if(msg.has(PAUSE)){
                    if(msg.get(PAUSE).getAsInt() == 1) stateManager.pause();
                }

                if(msg.has(PLAY)){
                    if(msg.get(PLAY).getAsInt() == 1) stateManager.play();
                }

                if(msg.has(HEROES)){
                    processHeroes(msg.get(HEROES).getAsJsonObject());
                }

            }

        }
    }

    private void processHeroes(JsonObject heroes){
        JsonObject mage = heroes.get(MAGE).getAsJsonObject();
        this.stateManager.getPlayState().getMage().move(
                mage.get(X).getAsFloat(),
                mage.get(Y).getAsFloat(),
                mage.get(ANGLE).getAsInt()
        );
        for(JsonElement element: mage.getAsJsonArray(ACTIONS)){
            this.stateManager.getPlayState().getMage().activateAnimation(element.getAsInt());
        }

        JsonObject war = heroes.get(WAR).getAsJsonObject();
        this.stateManager.getPlayState().getWar().move(
                war.get(X).getAsFloat(),
                war.get(Y).getAsFloat(),
                war.get(ANGLE).getAsInt()
        );
        for(JsonElement element: war.getAsJsonArray(ACTIONS)){
            this.stateManager.getPlayState().getWar().activateAnimation(element.getAsInt());
        }
    }

}
