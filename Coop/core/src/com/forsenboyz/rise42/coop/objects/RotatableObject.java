package com.forsenboyz.rise42.coop.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class RotatableObject extends Object {

    protected int rotation;

    private final int originX;
    private final int originY;

    public RotatableObject(String texturePath, int x, int y) {
        this(new Texture(texturePath), x, y);
    }

    public RotatableObject(Texture texture, int x, int y) {
        super(texture, x, y);
        this.originX = this.texture.getWidth() / 2;
        this.originY = this.texture.getHeight() / 2;
        this.rotation = 0;
    }

    /*public RotatableObject(Texture texture, int x, int y, int rotation){
        this(texture, x, y);
        this.rotation = rotation;
    }*/

    public RotatableObject(String texturePath, int x, int y, int rotation){
        this(texturePath, x, y);
        this.rotation = rotation;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(texture, x, y, originX, originY, texture.getWidth(), texture.getHeight(), 1, 1, rotation,
                0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
