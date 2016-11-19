package com.forsenboyz.rise42.coop.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.forsenboyz.rise42.coop.App;
import com.forsenboyz.rise42.coop.network.MessageManager;

public class StateManager {

    private MessageManager messageManager;

    private PauseState pauseState;
    private PlayState playState;
    private State currentState;

    public StateManager(){
        this.messageManager = new MessageManager(App.HOST,App.PORT,this);

        this.pauseState = new PauseState(this.messageManager, false);
        this.playState = new PlayState(this.messageManager, false);
        this.currentState = this.pauseState;

        this.pause();
    }

    public void render(SpriteBatch sb){
        currentState.render(sb);
    }

    public void update(float delta){
        currentState.update(delta);
    }

    public void pause(){
        currentState.deactivate();
        pauseState.activate();
        currentState = pauseState;
    }

    public void play(){
        currentState.deactivate();
        playState.activate();
        currentState = playState;
    }

}
