package com.forsenboyz.rise42.coop.objects;

import com.badlogic.gdx.graphics.Texture;

public class Hero extends Object {

    public Hero(int x, int y){
        super(new Texture("ghost.png"),x,y);
    }

    public void moveHorizontally(int distance){
        this.x += distance;
    }
}
