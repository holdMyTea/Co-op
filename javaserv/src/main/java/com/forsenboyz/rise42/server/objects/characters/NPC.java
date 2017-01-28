package com.forsenboyz.rise42.server.objects.characters;

import com.forsenboyz.rise42.server.objects.Type;
import com.forsenboyz.rise42.server.objects.Object;
import com.forsenboyz.rise42.server.parser.Coordinates;

import java.util.ArrayDeque;


public class NPC extends Character {

    private Object target;
    private ArrayDeque<Coordinates> path;

    public NPC(float moveSpeed, int maxHP, float x, float y, int angle, int width, int height) {
        super(Type.Enemy, moveSpeed, maxHP, x, y, angle, width, height);
        this.path = new ArrayDeque<>();
    }

    public void chooseTarget(Hero first, Hero second) {
        if (distanceTo(first) < distanceTo(second)) {
            this.target = first;
        } else {
            this.target = second;
        }
    }

    public void moveAlongPath(){
        this.angle = angleTo(path.peek());
        move();
    }

    private void move() {
        this.x += this.moveSpeed * Math.cos(Math.toRadians(angle));
        this.y += this.moveSpeed * Math.sin(Math.toRadians(angle));
    }

    private float distanceTo(Object target) {
        return (float) Math.sqrt(
                Math.pow(this.getCentreX() - target.getCentreX(), 2)
                        + Math.pow(this.getCentreY() - target.getCentreY(), 2)
        );
    }

    public int angleTo(Object target) {
        return (int) Math.atan(
                (target.getCentreX() - this.getCentreX())
                        / (target.getCentreY() - this.getCentreY())
        );
    }

    public int angleTo(Coordinates target){
        return (int) Math.atan(
                (target.x - this.getCentreX())
                    / ((target.y)- this.getCentreY())
        );
    }

    public boolean hasCompletedPath(){
        return path.isEmpty();
    }

    public Object getTarget() {
        return target;
    }
}
