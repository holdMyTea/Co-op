package com.forsenboyz.rise42.coop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.forsenboyz.rise42.coop.App;
import com.forsenboyz.rise42.coop.input.InputProcessor;
import com.forsenboyz.rise42.coop.network.InputMessageHandler;
import com.forsenboyz.rise42.coop.network.OutputMessageHandler;

public class StateManager {

    private OutputMessageHandler outputMessageHandler;

    private PauseState pauseState;
    private PlayState playState;
    private State currentState;

    public StateManager(){
        this.outputMessageHandler = new OutputMessageHandler(App.HOST,App.PORT,this);

        InputProcessor inputProcessor = new InputProcessor();
        Gdx.input.setInputProcessor(new InputMultiplexer(inputProcessor));

        this.pauseState = new PauseState(this.outputMessageHandler, inputProcessor, false);
        this.playState = new PlayState(this.outputMessageHandler, inputProcessor, false);

        this.outputMessageHandler.connect();  // possible null, if move before states init

        this.currentState = this.pauseState;

        this.pause();
    }

    public void render(SpriteBatch sb, float delta){
        currentState.render(sb, delta);
    }

    public void update(float delta){
        outputMessageHandler.parseIncomes();
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
