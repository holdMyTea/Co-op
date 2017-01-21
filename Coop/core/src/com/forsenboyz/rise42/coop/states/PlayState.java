package com.forsenboyz.rise42.coop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.forsenboyz.rise42.coop.App;
import com.forsenboyz.rise42.coop.config.ConfigParser;
import com.forsenboyz.rise42.coop.input.InputProcessor;
import com.forsenboyz.rise42.coop.network.OutputMessageHandler;
import com.forsenboyz.rise42.coop.objects.AttachedAnimation;
import com.forsenboyz.rise42.coop.objects.Character;
import com.forsenboyz.rise42.coop.objects.Object;

import java.util.ArrayList;


public class PlayState extends State {

    // delta between inputs read
    private static final float INPUT_WAIT = 1.0f * 0.01f;

    private OrthographicCamera camera;

    // borders, which camera should stay in to hide the scenes
    private final int CAMERA_MIN_X;
    private final int CAMERA_MAX_X;
    private final int CAMERA_MIN_Y;
    private final int CAMERA_MAX_Y;

    private Object background;

    // player-controlled characters
    private Character mage;
    private Character war;

    // holds the reference to either mage or war, that determines character controlled by this client
    private Character currentHero;

    // last time input was processed
    private float lastInputTime;

    // indicates, whether hero's angle was changed, if true angle POSSIBLY will be submitted in OutputMessageHandler
    private boolean rotated;

    private TextureRegion projectileTexture;
    private ArrayList<Object> projectiles;

    PlayState(OutputMessageHandler outputMessageHandler, InputProcessor inputProcessor, boolean active) {
        super(outputMessageHandler, inputProcessor, active);

        lastInputTime = INPUT_WAIT;

        background = new Object("back.png", 0, 0);
        objects.add(0, background);

        camera = new OrthographicCamera(App.WIDTH, App.HEIGHT);

        CAMERA_MIN_X = (int) (background.getX() + camera.viewportWidth / 2);
        CAMERA_MAX_X = (int) (background.getWidth() + background.getX() - camera.viewportWidth / 2);
        CAMERA_MAX_Y = (int) (background.getY() + background.getHeight() - camera.viewportHeight / 2);
        CAMERA_MIN_Y = (int) (background.getY() + camera.viewportHeight / 2);

        TextureAtlas charAtlas = new TextureAtlas(Gdx.files.internal("characters.atlas"));

        TextureAtlas strikeAtlas = new TextureAtlas(Gdx.files.internal("strk.atlas"));

        this.projectileTexture = strikeAtlas.findRegion("mag-strk", 1);
        projectiles = new ArrayList<>();

        war = new Character(charAtlas.findRegion("war"), 1200, 1056, 0);
        war.addAnimation(
                0,
                new AttachedAnimation(
                        strikeAtlas.findRegions("war-strk"),
                        0.25f
                )
        );
        objects.add(war);

        mage = new Character(charAtlas.findRegion("mage"), 850, 1064, 180);
        mage.addAnimation(
                0,
                new AttachedAnimation(
                        strikeAtlas.findRegions("mag-strk"),
                        0.25f
                )
        );
        objects.add(mage);

        objects.addAll(ConfigParser.getBlocks());

        // default value
        currentHero = mage;
    }

    @Override
    protected void update(float delta) {
        super.update(delta);
        lastInputTime += delta;
        centerCamera(currentHero);
    }

    @Override
    protected void render(SpriteBatch sb, float delta) {
        sb.setProjectionMatrix(camera.combined);
        super.render(sb, delta);
        this.renderProjectiles(sb, delta);
    }

    @Override
    protected void handleInput() {
        super.handleInput();

        if (lastInputTime < INPUT_WAIT) {
            return;
        }

        lastInputTime = 0;

        float angle = 360 - new Vector2(
                (currentHero.getCentreX() - camera.viewportWidth/2  + inputProcessor.getMouseX() +
                        (camera.position.x - currentHero.getCentreX())) - currentHero.getCentreX(),
                (currentHero.getCentreY() - camera.viewportHeight/2 + inputProcessor.getMouseY() -
                        (camera.position.y - currentHero.getCentreY())) - currentHero.getCentreY()
        ).angle();

        if (currentHero.getAngle() != (int) angle) {
            rotated = true;
            currentHero.setAngle((int) angle);
        }

        //actually handling input
        if (inputProcessor.isHeldUp()) {
            outputMessageHandler.move(true);
        } else if (inputProcessor.isHeldDown()) {
            outputMessageHandler.move(false);
        } else if (inputProcessor.isHeldQ()) {
            outputMessageHandler.action(0, currentHero.getAngle());
        } else if (inputProcessor.isHeldZ()) {
            outputMessageHandler.pause();
        }
    }

    private void centerCamera(Object target) {
        int newX = (int) target.getCentreX();

        if (newX < CAMERA_MIN_X) {
            newX = CAMERA_MIN_X;
        } else if (newX > CAMERA_MAX_X) {
            newX = CAMERA_MAX_X;
        }

        int newY = (int) target.getCentreY();

        if (newY < CAMERA_MIN_Y) {
            newY = CAMERA_MIN_Y;
        } else if (newY > CAMERA_MAX_Y) {
            newY = CAMERA_MAX_Y;
        }

        camera.position.x = newX;
        camera.position.y = newY;
        camera.update();
    }

    public boolean hasRotated() {
        return rotated;
    }

    public void setInitialParameters(int variant) {
        if (variant == 0) {
            currentHero = mage;
        } else if (variant == 1) {
            currentHero = war;
        }
    }

    public Character getMage() {
        return mage;
    }

    public Character getWar() {
        return war;
    }

    public int updateRotation() {
        rotated = false;
        return currentHero.getAngle();
    }

    private void renderProjectiles(SpriteBatch sb, float delta) {
        for (Object obj : projectiles) {
            obj.render(sb, delta);
        }
    }

    public void setProjectiles(ArrayList<Object> projectiles) {
        this.projectiles = projectiles;
    }

}
