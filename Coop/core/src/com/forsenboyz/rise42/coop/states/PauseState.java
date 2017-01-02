package com.forsenboyz.rise42.coop.states;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.forsenboyz.rise42.coop.App;
import com.forsenboyz.rise42.coop.input.InputProcessor;
import com.forsenboyz.rise42.coop.network.MessageManager;
import com.forsenboyz.rise42.coop.objects.Object;

class PauseState extends State {

    private Object background;

    PauseState(final MessageManager messageManager, InputProcessor inputProcessor, boolean active) {
        super(messageManager, inputProcessor, active);
        background = new Object(new Texture("play.png"), 0, 0);
        objects.add(background);

        objects.add(new Object("play.png",0,0));
    }

    @Override
    public void activate() {
        super.activate();
        background.setTexture(
                        new Texture(
                                ScreenUtils.getFrameBufferPixmap(0, 0, App.WIDTH, App.HEIGHT)));
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if(inputProcessor.isHeldSpace()){
            messageManager.connect();
            messageManager.play();
        }
    }
}
