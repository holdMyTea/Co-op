package com.forsenboyz.rise42.coop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.forsenboyz.rise42.coop.App;
import com.forsenboyz.rise42.coop.input.InputProcessor;
import com.forsenboyz.rise42.coop.network.MessageManager;

public class StateManager {

    private MessageManager messageManager;

    private PauseState pauseState;
    private PlayState playState;
    private State currentState;

    public StateManager(){
        this.messageManager = new MessageManager(App.HOST,App.PORT,this);

        InputProcessor inputProcessor = new InputProcessor();
        Gdx.input.setInputProcessor(new InputMultiplexer(inputProcessor));

        this.pauseState = new PauseState(this.messageManager, inputProcessor, false);
        this.playState = new PlayState(this.messageManager, inputProcessor, false);

        this.messageManager.connect();  // possible null, if move before states init

        this.currentState = this.pauseState;

        this.pause();
    }

    public void render(SpriteBatch sb, float delta){
        currentState.render(sb, delta);
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

    public PlayState getPlayState(){
        return playState;
    }

}
