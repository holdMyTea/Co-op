package com.forsenboyz.rise42.coop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.forsenboyz.rise42.coop.network.MessageManager;
import com.forsenboyz.rise42.coop.objects.Object;
import com.forsenboyz.rise42.coop.objects.RotatableObject;

public class PlayState extends State {

    // delta between inputs read
    private static final float INPUT_WAIT = 2 * 0.01f;

    private RotatableObject hero;
    private RotatableObject anotherHero;

    private float lastInputTime;

    PlayState(MessageManager messageManager, boolean active) {
        super(messageManager, active);

        lastInputTime = INPUT_WAIT;

        TextureAtlas charAtlas = new TextureAtlas(Gdx.files.internal("characters.atlas"));

        hero = new RotatableObject(charAtlas.findRegion("mage",-1), 50, 100, 90);
        objects.add(hero);

        anotherHero = new RotatableObject(charAtlas.findRegion("war",-1), 400, 100, 90);
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
            RotatableObject buffer = hero;
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
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            messageManager.rotate(false);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            messageManager.rotate(true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            messageManager.move(true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            messageManager.move(false);
        } else if (Gdx.input.isKeyPressed(Input.Keys.Z)) {   //TODO: i need correctly working ESC button
            messageManager.pause();
        }
    }
}
