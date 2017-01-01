package com.forsenboyz.rise42.coop.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

public class Character extends RotatableObject {

    private HashMap<String, StatusAnimation> animations;

    public Character(TextureRegion region, int x, int y, int rotation) {
        super(region, x, y, rotation);
        animations = new HashMap<>();
    }

    public Character(TextureRegion region, int x, int y) {
        this(region, x, y, 0);
    }

    @Override
    public void render(SpriteBatch sb, float delta) {
        super.render(sb, delta);
        for(StatusAnimation a:animations.values()){
            if(a.isActive()){
                System.out.println("smth is active");
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

    public void addAnimation(String key, AttachedAnimation attachedAnimation){
        this.animations.put(key, new StatusAnimation(attachedAnimation));
    }

    public void activateAnimation(String key){
        if(animations.containsKey(key)){
            animations.get(key).activate();
        }
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
