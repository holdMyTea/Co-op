package com.forsenboyz.rise42.server.message;

import com.forsenboyz.rise42.server.network.Server;
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
    private Server server;

    public OutcomeProcessor(ObjectHolder objectHolder, Server server) {
        this.objectHolder = objectHolder;
        this.server = server;
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
        for (Projectile projectile : objectHolder.getProjectileManager().getProjectiles()) {
            array.add(projectile.toJson());
        }
        return array;
    }

    private void newMessage(String msg){
        server.spreadMessage(msg + ";");
    }

}
