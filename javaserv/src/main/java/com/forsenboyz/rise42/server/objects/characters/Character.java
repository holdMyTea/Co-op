package com.forsenboyz.rise42.server.objects.characters;

import static com.forsenboyz.rise42.server.message.JsonProperties.*;

import com.forsenboyz.rise42.server.collisions.CollisionDetector;
import com.forsenboyz.rise42.server.collisions.Direction;
import com.forsenboyz.rise42.server.objects.Object;
import com.forsenboyz.rise42.server.objects.RotatableObject;
import com.forsenboyz.rise42.server.objects.Type;
import com.forsenboyz.rise42.server.objects.actions.Action;
import com.forsenboyz.rise42.server.objects.actions.Castable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Character extends RotatableObject {

    float moveSpeed;

    ArrayList<Action> actions;

    int currentHP;
    int maxHP;

    public Character(Type type, float moveSpeed, int maxHP, float x, float y, int angle, int width, int height) {
        super(type, x, y, width, height, angle);
        this.moveSpeed = moveSpeed;
        this.actions = new ArrayList<>();

        this.maxHP = maxHP;
        this.currentHP = maxHP;
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

    public void addAction(int index, Action action) {
        this.actions.add(index, action);
    }

    @Override
    public boolean onCollided(Object other, int direction) {
        if (other.getType() == Type.Wall || other.getType() == Type.Hero || other.getType() == Type.Enemy) {
            switch (direction) {
                case Direction.TOP_LEFT:
                    if (this.getX2() - other.getX() > this.y - other.getY2()) {
                        this.setX2(other.getX() - CollisionDetector.SOME_GAP_CONST);
                    } else {
                        this.setY(other.getY2() + CollisionDetector.SOME_GAP_CONST);
                    }
                    break;
                case Direction.TOP:
                    this.setY(other.getY2() + CollisionDetector.SOME_GAP_CONST);
                    break;
                case Direction.TOP_RIGHT:
                    if (this.getX() - other.getX2() > this.y - other.getY2()) {
                        this.setX(other.getX2() + CollisionDetector.SOME_GAP_CONST);
                    } else {
                        this.setY(other.getY2() + CollisionDetector.SOME_GAP_CONST);
                    }
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
                    if (this.getX2() - other.getX() > this.getY2() - other.getY()) {
                        this.setX2(other.getX() - CollisionDetector.SOME_GAP_CONST);
                    } else {
                        this.setY2(other.getY() - CollisionDetector.SOME_GAP_CONST);
                    }
                    break;
                case Direction.BOT:
                    this.setY2(other.getY() - CollisionDetector.SOME_GAP_CONST);
                    break;
                case Direction.BOT_RIGHT:
                    if (this.getX() - other.getX2() > this.getY2() - other.getY()) {
                        this.setX(other.getX2() + CollisionDetector.SOME_GAP_CONST);
                    } else {
                        this.setY2(other.getY() - CollisionDetector.SOME_GAP_CONST);
                    }
                    break;
                case Direction.NO:
                    return false;
            }
            return true;
        } else if(other.getType() == Type.Projectile){
            this.currentHP--;
        }
        return false;
    }

    public boolean isDead() {
        return currentHP <= 0;
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
