package com.forsenboyz.rise42.coop.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Object {

    protected float x, y;
    protected TextureRegion textureRegion;

    public Object(Texture texture, float x, float y) {
        this(new TextureRegion(texture), x, y);
    }

    public Object(TextureRegion region, float x, float y) {
        this.textureRegion = region;
        this.x = x;
        this.y = y;

        System.out.println("Created Object: "+region.getRegionHeight()+" "+region.getRegionWidth());
    }

    public Object(String texturePath, float x, float y) {
        this(new Texture(texturePath), x, y);
    }

    public void render(SpriteBatch sb, float delta) {
        sb.draw(textureRegion, x, y);
    }

    public void setTexture(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public void setTexture(Texture texture) {this.textureRegion = new TextureRegion(texture);}

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth(){
        return textureRegion.getRegionWidth();
    }

    public int getHeight(){
        return textureRegion.getRegionHeight();
    }
}
