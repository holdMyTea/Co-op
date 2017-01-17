package com.forsenboyz.rise42.coop.network;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.forsenboyz.rise42.coop.objects.Object;
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

    private StateManager stateManager;
    private Connection connection;

    InputMessageHandler(StateManager stateManager, Connection connection) {
        this.stateManager = stateManager;
        this.connection = connection;
    }

    public void parse() {

        Queue<String> queue = connection.getIncomeMessages();

        while (!queue.isEmpty()) {
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

    private void processHeroes(JsonObject heroes) {
        JsonObject mage = heroes.get(MAGE).getAsJsonObject();
        this.stateManager.getPlayState().getMage().move(
                mage.get(X).getAsFloat(),
                mage.get(Y).getAsFloat(),
                mage.get(ANGLE).getAsInt()
        );
        for (JsonElement element : mage.getAsJsonArray(ACTIONS)) {
            this.stateManager.getPlayState().getMage().activateAnimation(element.getAsInt());
        }

        JsonObject war = heroes.get(WAR).getAsJsonObject();
        this.stateManager.getPlayState().getWar().move(
                war.get(X).getAsFloat(),
                war.get(Y).getAsFloat(),
                war.get(ANGLE).getAsInt()
        );
        for (JsonElement element : war.getAsJsonArray(ACTIONS)) {
            this.stateManager.getPlayState().getWar().activateAnimation(element.getAsInt());
        }
    }

    private void processProjectiles(JsonArray projectiles) {
        ArrayList<Object> list = new ArrayList<>();
        for (JsonElement obj : projectiles) {
            list.add(
                    new Object(
                            new TextureRegion(),
                            obj.getAsJsonObject().get(X).getAsFloat(),
                            obj.getAsJsonObject().get(Y).getAsFloat()
                    )
            );
        }
        this.stateManager.getPlayState().setProjectiles(list);
    }

}
