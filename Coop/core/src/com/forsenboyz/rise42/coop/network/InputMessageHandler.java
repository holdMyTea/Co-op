package com.forsenboyz.rise42.coop.network;

import com.forsenboyz.rise42.coop.objects.EnemyBuilder;
import com.forsenboyz.rise42.coop.objects.Object;
import com.forsenboyz.rise42.coop.objects.ProjectileBuilder;
import com.forsenboyz.rise42.coop.objects.RotatableObject;
import com.forsenboyz.rise42.coop.states.StateManager;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.Queue;

public class InputMessageHandler {

    private static final String INIT = "init";
    private static final String PLAY = "play";
    private static final String PAUSE = "pause";
    private static final String PROJECTILES = "projectiles";
    private static final String ENEMIES = "enemies";

    private static final String MAGE = "mage";
    private static final String WAR = "war";

    private static final String X = "x";
    private static final String Y = "y";
    private static final String ANGLE = "a";
    private static final String ACTIONS = "act";

    private static final String TYPE = "t";

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

            if (msg.has(MAGE)) {
                processMage(msg.get(MAGE).getAsJsonObject());
            }

            if (msg.has(WAR)) {
                processWar(msg.get(WAR).getAsJsonObject());
            }

            if (msg.has(PROJECTILES)) {
                processProjectiles(msg.get(PROJECTILES).getAsJsonArray());
            }

            if (msg.has(ENEMIES)) {
                processEnemies(msg.get(ENEMIES).getAsJsonArray());
            }
        }
    }

    private void processMage(JsonObject mage){
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
    }

    private void processWar(JsonObject war){
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
        Object[] objects = new Object[projectiles.size()];
        for (int i = 0; i < projectiles.size(); i++) {
            objects[i] = ProjectileBuilder.makeProjectile(
                    projectiles.get(i).getAsJsonObject().get(X).getAsFloat(),
                    projectiles.get(i).getAsJsonObject().get(Y).getAsFloat(),
                    projectiles.get(i).getAsJsonObject().get(ANGLE).getAsInt(),
                    projectiles.get(i).getAsJsonObject().get(TYPE).getAsInt()
            );
        }
        this.stateManager.getPlayState().setProjectiles(objects);
    }

    private void processEnemies(JsonArray enemies){
        RotatableObject[] objects = new RotatableObject[enemies.size()];

        for (int i = 0; i < enemies.size(); i++) {
            objects[i] = EnemyBuilder.buildSkeleton(
                    enemies.get(i).getAsJsonObject().get(X).getAsFloat(),
                    enemies.get(i).getAsJsonObject().get(Y).getAsFloat(),
                    enemies.get(i).getAsJsonObject().get(ANGLE).getAsInt()
            );
        }

        this.stateManager.getPlayState().setEnemies(objects);
    }

}
