package com.forsenboyz.rise42.coop.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Object {
    protected int x,y;
    protected Texture texture;

    public Object(Texture texture, int x, int y){
        this.texture = texture;
        this.x = x;
        this.y = y;
    }

    public void render(SpriteBatch sb){
        sb.draw(texture,x,y);
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
