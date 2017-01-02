package com.forsenboyz.rise42.coop.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AttachedAnimation {

    private Animation animation;

    private float stateTime;

    public AttachedAnimation(Array<? extends TextureRegion> regions, float frameDuration) {
        this.animation = new Animation(frameDuration, regions, Animation.PlayMode.LOOP);
        stateTime = 0;
    }

    public void render(SpriteBatch sb, RotatableObject object, float delta) {
        this.stateTime += delta;
        TextureRegion current = animation.getKeyFrame(stateTime);
        System.out.println("drawing animation at " + (object.getX() * (float) Math.cos(Math.toRadians(object.getRotation()))) + object.getWidth()
                + " " + (object.getY() * (float) Math.sin(Math.toRadians(object.getRotation()))) + object.getHeight());
        System.out.println("while object is at "+object.getX()+" "+object.getY());

        sb.draw(
                current,
                (object.getWidth() * (float) Math.cos(Math.toRadians(object.getRotation())) * compute(object.getRotation())) + object.getX(),
                (object.getHeight() * (float) Math.sin(Math.toRadians(object.getRotation()))* compute(object.getRotation())) + object.getY(),
                current.getRegionWidth() / 2,
                current.getRegionHeight() / 2,
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

    private float compute(int angle){
        if(angle <= 180){
            return angle/180;
        } else{
            return 1 - angle/180;
        }
    }
}
