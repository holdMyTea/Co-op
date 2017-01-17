package com.forsenboyz.rise42.coop.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AttachedAnimation {

    private Animation animation;

    // time from animation activated, current texture is chosen by it
    private float stateTime;

    private boolean active;

    public AttachedAnimation(Array<? extends TextureRegion> regions, float frameDuration) {
        this.animation = new Animation(frameDuration, regions, Animation.PlayMode.NORMAL);
        this.stateTime = 0;
        this.active = false;
    }

    public void render(SpriteBatch sb, RotatableObject object, float delta) {
        TextureRegion current = animation.getKeyFrame(stateTime);
        sb.draw(
                current,
                object.getX()+object.getOriginX()-(current.getRegionWidth()/2)
                        +1.5f*(object.getOriginX()*(float)Math.cos(Math.toRadians(object.getAngle()))),
                object.getY()+object.getOriginY()-(current.getRegionHeight()/2)
                        +1.5f*(object.getOriginY()*(float)Math.sin(Math.toRadians(object.getAngle()))),
                current.getRegionWidth()/2,
                current.getRegionHeight()/ 2,
                current.getRegionWidth(),
                current.getRegionHeight(),
                1,
                1,
                object.getAngle()
        );
        this.stateTime += delta;
    }

    public boolean hasFinished() {
        return this.animation.isAnimationFinished(this.stateTime);
    }

    public void activate(){
        this.active = true;
    }

    public void deactivate(){
        this.active = false;
        this.stateTime = 0;
    }

    public boolean isActive() {
        return active;
    }
}
