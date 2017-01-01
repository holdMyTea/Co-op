package com.forsenboyz.rise42.coop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.forsenboyz.rise42.coop.App;
import com.forsenboyz.rise42.coop.network.MessageManager;
import com.forsenboyz.rise42.coop.objects.Object;

class PauseState extends State {

    private Object background;
    private Stage stage;

    PauseState(final MessageManager messageManager, boolean active) {
        super(messageManager, active);
        background = new Object(new Texture("play.png"), 0, 0);
        objects.add(background);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Button playButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture("play.png"))));
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                messageManager.connect();
                messageManager.play();
                System.out.println("Clicked");
            }
        });
        stage.addActor(playButton);
    }

    @Override
    protected void render(SpriteBatch sb, float delta) {
        super.render(sb, delta);
        stage.draw();
    }

    @Override
    public void activate() {
        super.activate();
        background.setTexture(
                        new Texture(
                                ScreenUtils.getFrameBufferPixmap(0, 0, App.WIDTH, App.HEIGHT)));
    }
}
