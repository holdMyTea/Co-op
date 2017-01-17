package com.forsenboyz.rise42.coop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.forsenboyz.rise42.coop.App;
import com.forsenboyz.rise42.coop.input.InputProcessor;
import com.forsenboyz.rise42.coop.network.OutputMessageHandler;
import com.forsenboyz.rise42.coop.objects.Object;

class PauseState extends State {

    private Object background;

    PauseState(final OutputMessageHandler outputMessageHandler, InputProcessor inputProcessor, boolean active) {
        super(outputMessageHandler, inputProcessor, active);
        background = new Object(new Texture("play.png"), 0, 0);
        objects.add(background);

        objects.add(new Object("play.png", 0, 0));
    }

    @Override
    public void activate() {
        Gdx.app.postRunnable(
                () -> background.setTexture(
                        new Texture(
                                ScreenUtils.getFrameBufferPixmap(0, 0, App.WIDTH, App.HEIGHT)))
        );
        super.activate();
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if (inputProcessor.isHeldSpace()) {
            outputMessageHandler.connect();
            outputMessageHandler.play();
        }
    }
}
