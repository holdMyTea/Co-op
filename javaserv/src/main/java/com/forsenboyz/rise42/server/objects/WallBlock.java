package com.forsenboyz.rise42.server.objects;

import com.forsenboyz.rise42.server.collisions.Collision;

public class WallBlock {

    private int x;
    private int x2;
    private int y;
    private int y2;

    public Collision checkCollision(Object object){
        if(object.getX() < x2 && object.getX() > x){
            if(object.getY() < y2 && object.getY() > y){
                return Collision.ALL;
            }
        }
        return Collision.NO;
    }

    @Override
    public String toString() {
        return "x:" + x
                + ", x2:" + x2
                + ", y:" + y
                + ", y2:" + y2;
    }

}
