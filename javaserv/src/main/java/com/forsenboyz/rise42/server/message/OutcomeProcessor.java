package com.forsenboyz.rise42.server.message;

import com.forsenboyz.rise42.server.objects.ObjectHolder;
import com.forsenboyz.rise42.server.objects.projectiles.Projectile;
import com.google.gson.*;

public class OutcomeProcessor {

    private static final String MAGE = "mage";
    private static final String WAR = "war";
    private static final String PROJECTILES = "projectiles";
    private static final String HEROES = "heroes";

    private static final String PAUSE_MSG = "{\"pause\":1}";
    private static final String PLAY_MSG = "{\"play\":1}";

    private ObjectHolder objectHolder;

    // flag to prevent multiple sending of the same message
    private boolean alreadyRead;

    private String message;

    public OutcomeProcessor(ObjectHolder objectHolder) {
        this.objectHolder = objectHolder;
        this.message = "";
    }

    public synchronized void makeMessage() {
        JsonObject message = new JsonObject();
        message.add(PROJECTILES, makeProjectileMessages());
        message.add(HEROES, makeHeroMessages());

        newMessage(new Gson().toJson(message));
    }

    public void makePauseMessage(){
        newMessage(PAUSE_MSG);
    }

    public void makePlayMessage(){
        newMessage(PLAY_MSG);
    }

    private JsonElement makeHeroMessages() {
        JsonObject heroes = new JsonObject();

        heroes.add(MAGE, objectHolder.getMage().toJson());
        heroes.add(WAR, objectHolder.getWar().toJson());

        return heroes;
    }

    private JsonElement makeProjectileMessages() {
        JsonArray array = new JsonArray();
        for (Projectile projectile : objectHolder.getProjectiles()) {
            array.add(projectile.toJson());
        }
        return array;
    }

    private void newMessage(String msg){
        this.message = msg + ";"; // semicolon is used on client to split messages
        this.alreadyRead = false;
    }

    public synchronized String getMessage() {
        if(!alreadyRead) {
            this.alreadyRead = true;
            return message;
        } return null;
    }
}
