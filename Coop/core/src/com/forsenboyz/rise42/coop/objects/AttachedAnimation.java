package com.forsenboyz.rise42.coop.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AttachedAnimation {

    private Animation animation;

    private float stateTime;

    public AttachedAnimation(Array<? extends TextureRegion> regions, float frameDuration) {
        this.animation = new Animation(frameDuration, regions, Animation.PlayMode.NORMAL);
        stateTime = 0;
    }

    public void render(SpriteBatch sb, RotatableObject object, float delta) {
        this.stateTime += delta;
        TextureRegion current = animation.getKeyFrame(stateTime);
        sb.draw(
                current,
                object.getX()+object.getOriginX()-(current.getRegionWidth()/2)
                        +1.5f*(object.getOriginX()*(float)Math.cos(Math.toRadians(object.getRotation()))),
                object.getY()+object.getOriginY()-(current.getRegionHeight()/2)
                        +1.5f*(object.getOriginY()*(float)Math.sin(Math.toRadians(object.getRotation()))),
                current.getRegionWidth()/2,
                current.getRegionHeight()/ 2,
                current.getRegionWidth(),
                current.getRegionHeight(),
                1,
                1,
                object.getRotation()
        );
    }

    public boolean hasFinished() {
        return this.animation.isAnimationFinished(this.stateTime);
    }

    public void resetStateTime() {
        this.stateTime = 0;
    }

}
