package com.forsenboyz.rise42.coop.network;

import com.forsenboyz.rise42.coop.objects.Object;
import com.forsenboyz.rise42.coop.objects.ProjectileBuilder;
import com.forsenboyz.rise42.coop.states.StateManager;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.Queue;

public class InputMessageHandler {

    private static final String INIT = "init";
    private static final String PLAY = "play";
    private static final String PAUSE = "pause";
    private static final String HEROES = "heroes";
    private static final String PROJECTILES = "projectiles";

    private static final String MAGE = "mage";
    private static final String WAR = "war";

    private static final String X = "x";
    private static final String Y = "y";
    private static final String ANGLE = "a";
    private static final String ACTIONS = "act";

    private static final String TYPE = "t";
    private static final String DESTROYED = "destr";

    private StateManager stateManager;
    private Connection connection;

    InputMessageHandler(StateManager stateManager, Connection connection) {
        this.stateManager = stateManager;
        this.connection = connection;
    }

    void parse() {

        Queue<String> queue = connection.getIncomeMessages();

        while (!queue.isEmpty()) {
            System.out.println("Net input: " + queue.peek());
            JsonObject msg;
            try {
                msg = new JsonParser().parse(queue.poll()).getAsJsonObject();
            } catch (JsonSyntaxException ex) {
                return;
            }


            if (msg.has(INIT)) {
                this.stateManager.getPlayState().setInitialParameters(msg.get(INIT).getAsInt());
            }

            if (msg.has(PAUSE)) {
                if (msg.get(PAUSE).getAsInt() == 1) stateManager.pause();
            }

            if (msg.has(PLAY)) {
                if (msg.get(PLAY).getAsInt() == 1) stateManager.play();
            }

            if (msg.has(HEROES)) {
                processHeroes(msg.get(HEROES).getAsJsonObject());
            }

            if (msg.has(PROJECTILES)) {
                processProjectiles(msg.get(PROJECTILES).getAsJsonArray());
            }
        }
    }

    //TODO: duplicate code
    private void processHeroes(JsonObject heroes) {
        JsonObject mage = heroes.get(MAGE).getAsJsonObject();
        this.stateManager.getPlayState().getMage().move(
                mage.get(X).getAsFloat(),
                mage.get(Y).getAsFloat(),
                mage.get(ANGLE).getAsInt()
        );

        // if empty - not sent
        if (mage.has(ACTIONS)) {
            for (JsonElement element : mage.getAsJsonArray(ACTIONS)) {
                this.stateManager.getPlayState().getMage().activateAnimation(element.getAsInt());
            }
        }

        JsonObject war = heroes.get(WAR).getAsJsonObject();
        this.stateManager.getPlayState().getWar().move(
                war.get(X).getAsFloat(),
                war.get(Y).getAsFloat(),
                war.get(ANGLE).getAsInt()
        );
        if (war.has(ACTIONS)) {
            for (JsonElement element : war.getAsJsonArray(ACTIONS)) {
                this.stateManager.getPlayState().getWar().activateAnimation(element.getAsInt());
            }
        }
    }

    private void processProjectiles(JsonArray projectiles) {
        ArrayList<Object> list = new ArrayList<>();
        for (JsonElement element : projectiles) {
            list.add(
                    ProjectileBuilder.makeProjectile(
                            element.getAsJsonObject().get(X).getAsFloat(),
                            element.getAsJsonObject().get(Y).getAsFloat(),
                            element.getAsJsonObject().get(ANGLE).getAsInt(),
                            element.getAsJsonObject().get(TYPE).getAsInt()
                    )
            );
        }
        this.stateManager.getPlayState().setProjectiles(list);
    }

}
