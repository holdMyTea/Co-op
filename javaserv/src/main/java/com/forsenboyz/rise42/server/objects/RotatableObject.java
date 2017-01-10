package com.forsenboyz.rise42.server.objects;

public class RotatableObject extends Object {

    protected int angle;

    public RotatableObject(float x, float y, int width, int height, int angle) {
        super(x, y, width, height);
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
}
