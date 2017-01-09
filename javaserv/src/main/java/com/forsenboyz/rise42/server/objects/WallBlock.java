package com.forsenboyz.rise42.server.objects;

import com.forsenboyz.rise42.server.collisions.Direction;

public class WallBlock extends Object {

    public WallBlock(float x, float x2, float y, float y2) {
        super(x, x2, y, y2);
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

    @Override
    public String toString() {
        return "wall block x:" + x
                + ", x2:" + getX2()
                + ", y:" + y
                + ", y2:" + getY2();
    }

}
