package com.forsenboyz.rise42.server.objects;

import com.forsenboyz.rise42.server.collisions.Direction;

public class WallBlock {

    private int x;
    private int x2;
    private int y;
    private int y2;

    public Direction checkCollision(Object object){
        if(object.getX() > x && object.getX2() < x2){
            if(object.getY() > y && object.getY2() < y2){
                return Direction.ALL;
            } else if(object.getY() < y2 && object.getY2() > y2){
                return Direction.TOP;
            } else if(object.getY2() > y && object.getY() < y){
                return Direction.BOT;
            }
        } else if(object.getX2() > x && object.getX() < x){
            if(object.getY() > y && object.getY2() < y2){
                return Direction.LEFT;
            } else if(object.getY() < y2 && object.getY2() > y2){
                return Direction.TOP_LEFT;
            } else if(object.getY2() > y && object.getY() < y){
                return Direction.BOT_LEFT;
            }
        } else if(object.getX() < x2 && object.getX2() > x){
            if(object.getY() > y && object.getY2() < y2){
                return Direction.RIGHT;
            } else if(object.getY() < y2 && object.getY2() > y2){
                return Direction.TOP_RIGHT;
            } else if(object.getY2() > y && object.getY() < y){
                return Direction.BOT_RIGHT;
            }
        }
        return Direction.NO;
    }

    public int getX() {
        return x;
    }

    public int getX2() {
        return x2;
    }

    public int getY() {
        return y;
    }

    public int getY2() {
        return y2;
    }

    @Override
    public String toString() {
        return "x:" + x
                + ", x2:" + x2
                + ", y:" + y
                + ", y2:" + y2;
    }

}
