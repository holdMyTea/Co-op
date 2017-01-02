package com.forsenboyz.rise42.coop.states;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.forsenboyz.rise42.coop.input.InputProcessor;
import com.forsenboyz.rise42.coop.network.MessageManager;
import com.forsenboyz.rise42.coop.objects.Object;

import java.util.ArrayList;

abstract class State {

    protected InputProcessor inputProcessor;

    protected MessageManager messageManager;
    protected ArrayList<Object> objects;

    protected boolean active;

    State(MessageManager messageManager, InputProcessor inputProcessor, boolean active){
        this.messageManager = messageManager;
        this.active = active;
        this.inputProcessor = inputProcessor;

        objects = new ArrayList<Object>();
    }

    protected void render(SpriteBatch sb, float delta){
        for(Object o: objects){
            o.render(sb, delta);
        }
    }

    protected  void update(float delta){
        handleInput();
        if(!active){
            return;
        }
    }

    protected void handleInput(){}

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
