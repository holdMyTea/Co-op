package com.forsenboyz.rise42.server.objects;

import static com.forsenboyz.rise42.server.message.JsonProperties.*;

import com.forsenboyz.rise42.server.objects.actions.Action;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Character extends RotatableObject {

    private float moveSpeed;

    private ArrayList<Action> actions;

    public Character(float moveSpeed, float x, float y, int angle, int width, int height) {
        super(x, y, width, height, angle);
        this.moveSpeed = moveSpeed;
        this.actions = new ArrayList<>();
    }

    public void move(int angle, boolean forward) {
        if (forward) {
            this.x += this.moveSpeed * Math.cos(Math.toRadians(angle));
            this.y += this.moveSpeed * Math.sin(Math.toRadians(angle));
        } else {
            int reversedAngle = convertAngle(angle, 180);
            this.x += this.moveSpeed * Math.cos(Math.toRadians(reversedAngle));
            this.y += this.moveSpeed * Math.sin(Math.toRadians(reversedAngle));
        }
        this.angle = angle;
    }

    public void update(long currentTimeMillis) {
        for (Action action : this.actions) {
            action.update(currentTimeMillis);
        }
    }

    public void addAction(int index, Action action) {
        this.actions.add(index, action);
    }

    public boolean activateAction(int index, long currentTimeMillis) {
        System.out.println("ACTIVATING ACTION " + index);
        return this.actions.get(index).activate(currentTimeMillis);
    }

    public ArrayList<Action> getActions() {
        return this.actions;
    }

    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty(X, this.x);
        object.addProperty(Y, this.y);
        object.addProperty(ANGLE, this.angle);

        JsonArray array = new JsonArray();
        for (int i = 0; i < this.actions.size(); i++) {
            if (this.actions.get(i).isActive()) {
                array.add(i);
            }
        }
        object.add(ACTIONS, array);

        return object;
    }

    @Override
    public String toString() {
        return "hero x:" + x
                + ", x2:" + getX2()
                + ", y:" + y
                + ", y2:" + getY2();
    }
}
