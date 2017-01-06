package com.forsenboyz.rise42.coop.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.HashMap;

public class Character extends RotatableObject {

    private ArrayList<StatusAnimation> animations;

    public Character(TextureRegion region, float x, float y, int rotation) {
        super(region, x, y, rotation);
        animations = new ArrayList<>();
    }

    public Character(TextureRegion region, float x, float y) {
        this(region, x, y, 0);
    }

    @Override
    public void render(SpriteBatch sb, float delta) {
        super.render(sb, delta);
        for(StatusAnimation a:animations){
            if(a.isActive()){
                AttachedAnimation animation = a.getAnimation();
                if(animation.hasFinished()){
                    animation.resetStateTime();
                    a.deactivate();
                    continue;
                }
                animation.render(sb, this, delta);
            }
        }
    }

    public void addAnimation(int index, AttachedAnimation attachedAnimation){
        this.animations.add(index, new StatusAnimation(attachedAnimation));
    }

    public void activateAnimation(int index){
        if(animations.get(index) != null){
            animations.get(index).activate();
        }
    }

    public void move(float x, float y, int angle){
        this.x = x;
        this.y = y;
        this.rotation = angle;
    }
}

class StatusAnimation{

    private AttachedAnimation animation;
    private boolean active;

    public StatusAnimation(AttachedAnimation animation) {
        this.animation = animation;
        this.active = false;
    }

    public void activate(){
        this.active = true;
    }

    public void deactivate(){
        this.active = false;
    }

    public AttachedAnimation getAnimation() {
        return animation;
    }

    public boolean isActive() {
        return active;
    }
}
