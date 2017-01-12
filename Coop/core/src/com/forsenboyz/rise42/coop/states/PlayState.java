package com.forsenboyz.rise42.coop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.forsenboyz.rise42.coop.App;
import com.forsenboyz.rise42.coop.config.ConfigParser;
import com.forsenboyz.rise42.coop.input.InputProcessor;
import com.forsenboyz.rise42.coop.network.MessageManager;
import com.forsenboyz.rise42.coop.objects.AttachedAnimation;
import com.forsenboyz.rise42.coop.objects.Character;
import com.forsenboyz.rise42.coop.objects.Object;


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
    private Character thisHero;

    // last time input was processed
    private float lastInputTime;

    // indicates, whether hero's angle was changed, if true angle POSSIBLY will be submitted in MessageManager
    private boolean rotated;

    PlayState(MessageManager messageManager, InputProcessor inputProcessor, boolean active) {
        super(messageManager, inputProcessor, active);

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

        mage = new Character(charAtlas.findRegion("mage"), 850, 1064, 180);
        mage.addAnimation(
                0,
                new AttachedAnimation(
                        strikeAtlas.findRegions("mag-strk"),
                        0.25f
                )
        );
        objects.add(mage);

        war = new Character(charAtlas.findRegion("war"), 1200, 1056, 0);
        war.addAnimation(
                0,
                new AttachedAnimation(
                        strikeAtlas.findRegions("war-strk"),
                        0.25f
                )
        );
        objects.add(war);

        objects.addAll(ConfigParser.getBlocks());

        // default value
        thisHero = mage;
    }

    @Override
    protected void render(SpriteBatch sb, float delta) {
        sb.setProjectionMatrix(camera.combined);
        super.render(sb, delta);
    }

    @Override
    protected void update(float delta) {
        super.update(delta);
        lastInputTime += delta;
        centerCamera(thisHero);
    }

    @Override
    protected void handleInput() {
        super.handleInput();

        if (lastInputTime < INPUT_WAIT) {
            return;
        }

        lastInputTime = 0;

        //TODO: normal mouse tracking
        // rotating mage according to mouse position, relatively to the center of the screen
        float angle = (float) Math.atan2(
                inputProcessor.getMouseY() - Gdx.graphics.getHeight() / 2,
                inputProcessor.getMouseX() - Gdx.graphics.getWidth() / 2
        ) * MathUtils.radiansToDegrees;
        if (angle < 0) angle = 360 - (angle + 360);
        else angle = 360 - angle;

        if (thisHero.getRotation() != (int) angle) {
            rotated = true;
            thisHero.setRotation((int) angle);
        }

        //actually handling input
        if (inputProcessor.isHeldUp()) {
            messageManager.move(true);
        } else if (inputProcessor.isHeldDown()) {
            messageManager.move(false);
        } else if (inputProcessor.isHeldQ()) {
            messageManager.animation(0, thisHero.getRotation());
        } else if (inputProcessor.isHeldZ()) {
            messageManager.pause();
        }
    }

    private void centerCamera(Object target) {
        camera.unproject(new Vector3(target.getX(), target.getY(), 0));

        int targetX = (int) target.getX();

        if (targetX < CAMERA_MIN_X) {
            camera.position.x = CAMERA_MIN_X;
        } else if (targetX > CAMERA_MAX_X) {
            camera.position.x = CAMERA_MAX_X;
        } else camera.position.x = targetX;

        int targetY = (int) target.getY();

        if (targetY < CAMERA_MIN_Y) {
            camera.position.y = CAMERA_MIN_Y;
        } else if (targetY > CAMERA_MAX_Y) {
            camera.position.y = CAMERA_MAX_Y;
        } else camera.position.y = targetY;

        camera.update();
    }

    public boolean hasRotated() {
        return rotated;
    }

    public void setInitialParameters(int variant) {
        if (variant == 0) {
            thisHero = mage;
        } else if (variant == 1) {
            thisHero = war;
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
        return thisHero.getRotation();
    }
}
