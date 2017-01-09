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


    /**
     moves Object along the X axis so its left edge will be equal to parameter x
     * @param x desired X position of an Object's left edge
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     moves Object along the Y axis so its left edge will be equal to parameter y
     * @param y desired Y position of an Object's left edge
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     moves Object along the X axis so its right edge will be equal to parameter x
     * @param x2 desired X position of an Object's right edge
     */
    public void setX2(float x2){
        this.x = x2 - width;
    }

    /**
     moves Object along the Y axis so its right edge will be equal to parameter y
     * @param y2 desired Y position of an Object's right edge
     */
    public void setY2(float y2){
        this.y = y2 - height;
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
}
