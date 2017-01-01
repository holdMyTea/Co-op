package com.forsenboyz.rise42.coop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.forsenboyz.rise42.coop.network.MessageManager;
import com.forsenboyz.rise42.coop.objects.AttachedAnimation;
import com.forsenboyz.rise42.coop.objects.Character;
import com.forsenboyz.rise42.coop.objects.Object;

public class PlayState extends State {

    // delta between inputs read
    private static final float INPUT_WAIT = 2 * 0.01f;

    private Character hero;
    private Character anotherHero;

    private float lastInputTime;

    PlayState(MessageManager messageManager, boolean active) {
        super(messageManager, active);

        lastInputTime = INPUT_WAIT;

        TextureAtlas charAtlas = new TextureAtlas(Gdx.files.internal("characters.atlas"));

        hero = new Character(charAtlas.findRegion("mage"), 50, 100);
        hero.addAnimation(
                "strike",
                new AttachedAnimation(
                        new TextureAtlas("war_strk.atlas").findRegions("war-strk"),
                        0.25f
                )
        );
        objects.add(hero);

        anotherHero = new Character(charAtlas.findRegion("war"), 400, 100, 90);
        objects.add(anotherHero);

        objects.add(0, new Object("back.png", 0, 0));
    }

    public void moveHero(int x, int y) {
        this.hero.setPosition(x, y);
    }

    public void moveAnotherHero(int x, int y) {
        this.anotherHero.setPosition(x, y);
    }

    public void rotateHero(int angle) {
        this.hero.setRotation(angle);
    }

    public void rotateAnotherHero(int angle) {
        this.anotherHero.setRotation(angle);
    }

    public void setInitialParameters(int variant) {
        if (variant == 1) {
            Character buffer = hero;
            hero = anotherHero;
            anotherHero = buffer;
        }
    }

    @Override
    protected void update(float delta) {
        lastInputTime += delta;
        if (lastInputTime > INPUT_WAIT) {
            handleInput();
            lastInputTime = 0;
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            messageManager.rotate(false);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            messageManager.rotate(true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            messageManager.move(true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            messageManager.move(false);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            hero.activateAnimation("strike");
        } else if (Gdx.input.isKeyPressed(Input.Keys.Z)) {   //TODO: i need correctly working ESC button
            messageManager.pause();
        }
    }
}
