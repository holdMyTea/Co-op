package com.forsenboyz.rise42.coop.states;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.forsenboyz.rise42.coop.App;
import com.forsenboyz.rise42.coop.input.InputProcessor;
import com.forsenboyz.rise42.coop.network.MessageManager;
import com.forsenboyz.rise42.coop.objects.Object;

import java.util.ArrayList;

abstract class State {

    protected InputProcessor inputProcessor;

    protected MessageManager messageManager;
    protected ArrayList<Object> objects;

    protected boolean active;

    State(MessageManager messageManager, InputProcessor inputProcessor, boolean active) {
        this.messageManager = messageManager;
        this.active = active;
        this.inputProcessor = inputProcessor;

        objects = new ArrayList<Object>();
    }

    protected void render(SpriteBatch sb, float delta) {
        for (Object o : objects) {
            o.render(sb, delta);
        }
    }

    protected void update(float delta) {
        if (!active){
           return;
        }
        handleInput();
    }

    protected void handleInput() {
    }

    public void activate() {
        this.active = true;
        synchronized (this) {
            this.notify();
        }
    }

    public void deactivate() {
        this.active = false;
    }

    public boolean isActive() {
        return active;
    }
}
