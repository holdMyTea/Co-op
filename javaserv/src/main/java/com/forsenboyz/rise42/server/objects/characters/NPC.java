package com.forsenboyz.rise42.server.objects.characters;

import com.forsenboyz.rise42.server.objects.Type;
import com.forsenboyz.rise42.server.objects.Object;

public class NPC extends Character {

    private float previousX;
    private float previousY;

    public NPC(float moveSpeed, int maxHP, float x, float y, int angle, int width, int height) {
        super(Type.Enemy, moveSpeed, maxHP, x, y, angle, width, height);
    }

    public void move(Hero first, Hero second) {
        this.previousX = this.x;
        this.previousY = this.y;

        if (distanceTo(first) < distanceTo(second)) {
            this.angle = angleTo(first);
        } else {
            this.angle = angleTo(second);
        }
        changeCoordinates();
    }

    public void afterMove() {
        if ((Math.abs(this.x - previousX) < 1)
                &&
                (Math.abs(this.y - previousY) < 1)
                ) {
            if(this.angle < 0){
                this.angle = -90;
            } else if(this.angle > 0){
                this.angle = 90;
            }
            changeCoordinates();
        }
    }

    private void changeCoordinates() {
        this.x += this.moveSpeed * Math.cos(Math.toRadians(angle));
        this.y += this.moveSpeed * Math.sin(Math.toRadians(angle));
    }

    private float distanceTo(Object target) {
        return (float) Math.sqrt(
                Math.pow(this.getCentreX() - target.getCentreX(), 2)
                        + Math.pow(this.getCentreY() - target.getCentreY(), 2)
        );
    }

    private int angleTo(Object target) {
        return (int) Math.atan(
                (target.getCentreX() - this.getCentreX())
                        / (target.getCentreY() - this.getCentreY())
        );
    }
}
