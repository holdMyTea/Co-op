package com.forsenboyz.rise42.server.objects.characters;

import com.forsenboyz.rise42.server.objects.Type;

public class Hero extends Character {

    public Hero(float moveSpeed, int maxHP, float x, float y, int angle, int width, int height) {
        super(Type.Hero, moveSpeed, maxHP, x, y, angle, width, height);
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
}
