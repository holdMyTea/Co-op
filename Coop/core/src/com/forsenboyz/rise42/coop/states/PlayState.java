package com.forsenboyz.rise42.coop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.forsenboyz.rise42.coop.App;
import com.forsenboyz.rise42.coop.input.InputProcessor;
import com.forsenboyz.rise42.coop.network.MessageManager;
import com.forsenboyz.rise42.coop.objects.AttachedAnimation;
import com.forsenboyz.rise42.coop.objects.Character;
import com.forsenboyz.rise42.coop.objects.Object;

public class PlayState extends State {

    // delta between inputs read
    private static final float INPUT_WAIT = 7.5f * 0.01f;

    private OrthographicCamera camera;

    private Character hero;
    private Character anotherHero;

    private float lastInputTime;

    PlayState(MessageManager messageManager, InputProcessor inputProcessor, boolean active) {
        super(messageManager, inputProcessor, active);

        camera = new OrthographicCamera(App.WIDTH, App.HEIGHT);

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

    public void moveHero(float x, float y) {
        this.hero.setPosition(x, y);
    }

    public void moveAnotherHero(float x, float y) {
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
    protected void render(SpriteBatch sb, float delta) {
        camera.unproject(new Vector3(hero.getX(), hero.getY(), 0));
        camera.position.x = hero.getX();
        camera.position.y = hero.getY();
        camera.update();
        sb.setProjectionMatrix(camera.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render(sb, delta);
    }

    @Override
    protected void update(float delta) {
        super.update(delta);
        lastInputTime += delta;
        if (lastInputTime > INPUT_WAIT) {
            handleInput();
            lastInputTime = 0;
        }
    }

    @Override
    protected void handleInput() {
        super.handleInput();
        if (inputProcessor.isHeldLeft()) {
            messageManager.rotate(false);
        } else if (inputProcessor.isHeldRight()) {
            messageManager.rotate(true);
        } else if (inputProcessor.isHeldUp()) {
            messageManager.move(true);
        } else if (inputProcessor.isHeldDown()) {
            messageManager.move(false);
        } else if (inputProcessor.isHeldQ()) {
            hero.activateAnimation("strike");
        } else if (inputProcessor.isHeldZ()) {   //TODO: i need correctly working ESC button
            messageManager.pause();
        }
    }
}
