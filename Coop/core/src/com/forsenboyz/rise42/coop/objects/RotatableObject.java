package com.forsenboyz.rise42.coop.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class RotatableObject extends Object {

    private int rotation;

    private final int originX;
    private final int originY;

    public RotatableObject(TextureRegion textureRegion, int x, int y, int rotation) {
        super(textureRegion, x, y);
        this.originX = this.textureRegion.getRegionWidth() / 2;
        this.originY = this.textureRegion.getRegionHeight() / 2;
        this.rotation = rotation;
    }

    public RotatableObject(Texture texture, int x, int y, int rotation) {
        this(new TextureRegion(texture), x, y, rotation);
    }

    public RotatableObject(String texturePath, int x, int y) {
        this(new Texture(texturePath), x, y);
    }

    public RotatableObject(Texture texture, int x, int y) {
        this(texture, x, y, 0);
    }

    public RotatableObject(TextureRegion textureRegion, int x, int y) {
        this(textureRegion, x, y, 0);
    }

    public RotatableObject(String texturePath, int x, int y, int rotation) {
        this(new Texture(texturePath), x, y, rotation);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(textureRegion, x, y, originX, originY, textureRegion.getRegionWidth(), textureRegion.getRegionHeight(),
                1, 1, rotation);
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
