package com.forsenboyz.rise42.server.message;

import com.forsenboyz.rise42.server.objects.Hero;
import com.forsenboyz.rise42.server.objects.ObjectHolder;
import com.forsenboyz.rise42.server.objects.projectiles.Projectile;
import com.google.gson.*;

public class OutcomeProcessor {

    private ObjectHolder objectHolder;

    private String message;

    public OutcomeProcessor(ObjectHolder objectHolder) {
        this.objectHolder = objectHolder;
        this.message = "";
    }

    public void makeMessage() {
        JsonObject message = new JsonObject();
        message.add("projectiles", makeProjectileMessages());
        message.add("heroes", makeHeroMessages());

        this.message = new Gson().toJson(message);
    }

    private JsonElement makeHeroMessages() {
        JsonObject heroes = new JsonObject();

        heroes.add("mage", makeHeroJson(objectHolder.getMage()));
        heroes.add("war", makeHeroJson(objectHolder.getWar()));

        return heroes;
    }

    private JsonElement makeProjectileMessages() {
        JsonArray array = new JsonArray();
        for (Projectile projectile : objectHolder.getProjectiles()) {
            JsonObject object = new JsonObject();
            object.addProperty("x", projectile.getX());
            object.addProperty("y", projectile.getY());
            object.addProperty("a", projectile.getAngle());
            object.addProperty("type", projectile.getType());
            array.add(object);
        }
        return array;
    }

    private JsonElement makeHeroJson(Hero hero) {
        JsonObject object = new JsonObject();
        object.addProperty("x", hero.getX());
        object.addProperty("y", hero.getY());
        object.addProperty("a", hero.getAngle());
        return object;
    }

    public String getMessage() {
        return message;
    }
}
