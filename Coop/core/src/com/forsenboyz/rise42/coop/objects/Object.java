package com.forsenboyz.rise42.coop.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.concurrent.atomic.AtomicInteger;

public class Object {

    private AtomicInteger x, y;
    TextureRegion textureRegion;

    public Object(Texture texture, float x, float y) {
        this(new TextureRegion(texture), x, y);
    }

    public Object(TextureRegion region, float x, float y) {
        this.textureRegion = region;
        this.x = new AtomicInteger(Float.floatToIntBits(x));
        this.y = new AtomicInteger(Float.floatToIntBits(y));
        System.out.println("Created Object: "+region.getRegionHeight()+" "+region.getRegionWidth());
    }

    public Object(String texturePath, float x, float y) {
        this(new Texture(texturePath), x, y);
    }

    public void render(SpriteBatch sb, float delta) {
        sb.draw(textureRegion,
                Float.intBitsToFloat(x.get()),
                Float.intBitsToFloat(y.get())
        );
    }

    public void setTexture(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public void setTexture(Texture texture) {this.textureRegion = new TextureRegion(texture);}

    synchronized void setPosition(float x, float y) {
        this.x.set(Float.floatToIntBits(x));
        this.y.set(Float.floatToIntBits(y));
    }

    public synchronized float getX() {
        return Float.intBitsToFloat(x.get());
    }

    public synchronized float getY() {
        return Float.intBitsToFloat(y.get());
    }

    public int getWidth(){
        return textureRegion.getRegionWidth();
    }

    public int getHeight(){
        return textureRegion.getRegionHeight();
    }

    @Override
    public String toString() {
        return "x:"+this.getX()+ ", y:"+this.getY();
    }
}
