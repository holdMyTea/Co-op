package com.forsenboyz.rise42.server.objects;

import com.forsenboyz.rise42.server.collisions.Direction;

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

    public Direction checkCollision(Object other){
        float x2 = this.getX2(), y2 = this.getY2();
        if(other.getX() > x && other.getX2() < x2){
            if(other.getY() > y && other.getY2() < y2){
                return Direction.ALL;
            } else if(other.getY() < y2 && other.getY2() > y2){
                return Direction.TOP;
            } else if(other.getY2() > y && other.getY() < y){
                return Direction.BOT;
            }
        } else if(other.getX2() > x && other.getX() < x){
            if(other.getY() > y && other.getY2() < y2){
                return Direction.LEFT;
            } else if(other.getY() < y2 && other.getY2() > y2){
                return Direction.TOP_LEFT;
            } else if(other.getY2() > y && other.getY() < y){
                return Direction.BOT_LEFT;
            }
        } else if(other.getX() < x2 && other.getX2() > x){
            if(other.getY() > y && other.getY2() < y2){
                return Direction.RIGHT;
            } else if(other.getY() < y2 && other.getY2() > y2){
                return Direction.TOP_RIGHT;
            } else if(other.getY2() > y && other.getY() < y){
                return Direction.BOT_RIGHT;
            }
        }
        return Direction.NO;
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

    @Override
    public String toString() {
        return "Object: x:"+x+"-"+getX2()+", y:"+y+"-"+getY2();
    }
}
