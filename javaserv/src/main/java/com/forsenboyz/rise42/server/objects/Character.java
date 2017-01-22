package com.forsenboyz.rise42.server.objects;

import static com.forsenboyz.rise42.server.message.JsonProperties.*;

import com.forsenboyz.rise42.server.collisions.CollisionDetector;
import com.forsenboyz.rise42.server.collisions.Direction;
import com.forsenboyz.rise42.server.objects.actions.Action;
import com.forsenboyz.rise42.server.objects.actions.Castable;
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

    public Castable[] update(long currentTimeMillis) {
        return this.actions.stream()
                .peek(action -> action.update(currentTimeMillis))
                .filter(Action::isReady)
                .map(action -> action.cast(currentTimeMillis))
                .toArray(Castable[]::new);
    }

    public boolean startAction(int index, long currentTimeMillis) {
        System.out.println("ACTIVATING ACTION " + index);
        return this.actions.get(index).startCasting(currentTimeMillis);
    }

    void addAction(int index, Action action) {
        this.actions.add(index, action);
    }

    @Override
    public void onCollided(Object other, int direction) {
        switch (direction) {
            case Direction.TOP_LEFT:
                this.setY(other.getY2() + CollisionDetector.SOME_GAP_CONST);
                this.setX2(other.getX() - CollisionDetector.SOME_GAP_CONST);
                break;
            case Direction.TOP:
                this.setY(other.getY2() + CollisionDetector.SOME_GAP_CONST);
                break;
            case Direction.TOP_RIGHT:
                this.setY(other.getY2() + CollisionDetector.SOME_GAP_CONST);
                this.setX(other.getX2() + CollisionDetector.SOME_GAP_CONST);
                break;
            case Direction.LEFT:
                this.setX2(other.getX() - CollisionDetector.SOME_GAP_CONST);
                break;
            case Direction.ALL:
                break;
            case Direction.RIGHT:
                this.setX(other.getX2() + CollisionDetector.SOME_GAP_CONST);
                break;
            case Direction.BOT_LEFT:
                this.setY2(other.getY() - CollisionDetector.SOME_GAP_CONST);
                this.setX2(other.getX() - CollisionDetector.SOME_GAP_CONST);
                break;
            case Direction.BOT:
                this.setY2(other.getY() - CollisionDetector.SOME_GAP_CONST);
                break;
            case Direction.BOT_RIGHT:
                this.setY2(other.getY() - CollisionDetector.SOME_GAP_CONST);
                this.setX(other.getX2() + CollisionDetector.SOME_GAP_CONST);
                break;
        }
    }

    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        object.addProperty(X, this.x);
        object.addProperty(Y, this.y);
        object.addProperty(ANGLE, this.angle);

        JsonArray array = new JsonArray();
        for (int i = 0; i < this.actions.size(); i++) {
            if (this.actions.get(i).isCasting()) {
                array.add(i);
            }
        }
        if (array.size() > 0) {
            object.add(ACTIONS, array);
        }

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
