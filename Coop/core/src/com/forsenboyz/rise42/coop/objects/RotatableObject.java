package com.forsenboyz.rise42.coop.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class RotatableObject extends Object {

    private int angle;

    private final int originX;
    private final int originY;

    public RotatableObject(TextureRegion textureRegion, float x, float y, int angle) {
        super(textureRegion, x, y);
        this.originX = this.textureRegion.getRegionWidth() / 2;
        this.originY = this.textureRegion.getRegionHeight() / 2;
        this.angle = angle;
    }

    public RotatableObject(Texture texture, float x, float y, int angle) {
        this(new TextureRegion(texture), x, y, angle);
    }

    public RotatableObject(String texturePath, float x, float y) {
        this(new Texture(texturePath), x, y);
    }

    public RotatableObject(Texture texture, float x, float y) {
        this(texture, x, y, 0);
    }

    public RotatableObject(TextureRegion textureRegion, float x, float y) {
        this(textureRegion, x, y, 0);
    }

    public RotatableObject(String texturePath, float x, float y, int angle) {
        this(new Texture(texturePath), x, y, angle);
    }

    @Override
    public void render(SpriteBatch sb, float delta) {
        sb.draw(textureRegion, getX(), getY(), originX, originY, textureRegion.getRegionWidth(),
                textureRegion.getRegionHeight(), 1, 1, angle);
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }

    int getOriginX() {
        return originX;
    }

    int getOriginY() {
        return originY;
    }

    @Override
    public String toString() {
        return super.toString()+", angle:"+this.angle;
    }
}
