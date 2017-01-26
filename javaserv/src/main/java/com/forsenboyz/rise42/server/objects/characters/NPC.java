package com.forsenboyz.rise42.server.objects.characters;

import com.forsenboyz.rise42.server.objects.Type;
import com.forsenboyz.rise42.server.objects.Object;

public class NPC extends Character {

    public NPC(float moveSpeed, int maxHP, float x, float y, int angle, int width, int height) {
        super(Type.Enemy, moveSpeed, maxHP, x, y, angle, width, height);
    }

    public void move(Hero first, Hero second) {
        if (distanceTo(first) < distanceTo(second)) {
            moveTo(first);
        } else {
            moveTo(second);
        }
    }

    private void moveTo(Object target) {
        // learn to walk again
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
