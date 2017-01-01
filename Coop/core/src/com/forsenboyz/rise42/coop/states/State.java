package com.forsenboyz.rise42.coop.states;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.forsenboyz.rise42.coop.network.MessageManager;
import com.forsenboyz.rise42.coop.objects.Object;

import java.util.ArrayList;

abstract class State {

    protected MessageManager messageManager;
    protected ArrayList<Object> objects;

    protected boolean active;

    State(MessageManager messageManager, boolean active){
        this.messageManager = messageManager;
        this.active = active;

        objects = new ArrayList<Object>();
    }

    protected void render(SpriteBatch sb, float delta){
        for(Object o: objects){
            o.render(sb, delta);
        }
    }

    protected  void update(float delta){
        if(!active){
            return;
        }
    }

    public void activate(){
        this.active = true;
    }

    public void deactivate(){
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }
}
