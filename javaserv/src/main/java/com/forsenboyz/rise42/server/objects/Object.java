package com.forsenboyz.rise42.server.objects;

public class Object {

    protected float x;
    protected float y;
    protected int width;
    protected int height;

    public Object(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

    }

    public Object(float x, float x2, float y, float y2) {
        this.x = x;
        this.y = y;

        this.width = (int) Math.abs(x2 - x);
        this.height = (int) Math.abs(y2 - y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getX2() {
        return x + width;
    }

    public float getY2() {
        return y + height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX2(float x2){
        this.x = x2 - width;
    }

    public void setY2(float y2){
        this.y = y2 - height;
    }
}
