package com.forsenboyz.rise42.coop.states;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.forsenboyz.rise42.coop.input.InputProcessor;
import com.forsenboyz.rise42.coop.network.OutputMessageHandler;
import com.forsenboyz.rise42.coop.objects.Object;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

abstract class State {

    protected InputProcessor inputProcessor;

    protected OutputMessageHandler outputMessageHandler;
    protected ArrayList<Object> objects;

    private AtomicBoolean active;

    State(OutputMessageHandler outputMessageHandler, InputProcessor inputProcessor, boolean active) {
        this.outputMessageHandler = outputMessageHandler;
        this.active = new AtomicBoolean(false);
        this.inputProcessor = inputProcessor;

        objects = new ArrayList<>();
    }

    protected void render(SpriteBatch sb, float delta) {
        for (Object o : objects) {
            o.render(sb, delta);
        }
    }

    protected void update(float delta) {
        if (!active.get()){
           return;
        }
        handleInput();
    }

    protected void handleInput() {
    }

    public void activate() {
        synchronized (this) {
            this.active.set(true);
            this.notify();
        }
    }

    public void deactivate() {
        this.active.set(false);
    }

    public boolean isActive() {
        return active.get();
    }
}
