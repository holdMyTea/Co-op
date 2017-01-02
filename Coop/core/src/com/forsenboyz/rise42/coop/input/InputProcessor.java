package com.forsenboyz.rise42.coop.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class InputProcessor extends InputAdapter{

    private boolean heldUp;
    private boolean heldDown;
    private boolean heldLeft;
    private boolean heldRight;

    private boolean heldQ;
    private boolean heldZ;

    private boolean heldSpace;

    public InputProcessor() {
        super();
        heldUp = false;
        heldDown = false;
        heldLeft = false;
        heldRight = false;

        heldQ = false;
        heldZ = false;

        heldSpace = false;
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.DPAD_LEFT) {
            heldLeft = true;
        } else if (keyCode == Input.Keys.DPAD_RIGHT) {
            heldRight = true;
        } else if (keyCode == Input.Keys.DPAD_UP) {
            heldUp = true;
        } else if (keyCode == Input.Keys.DPAD_DOWN) {
            heldDown = true;
        } else if (keyCode == Input.Keys.Q) {
            heldQ = true;
        } else if (keyCode == Input.Keys.Z) {   //TODO: i need correctly working ESC button
            heldZ = true;
        } else if (keyCode == Input.Keys.SPACE) {
            heldSpace = true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keyCode) {
        if (keyCode == Input.Keys.DPAD_LEFT) {
            heldLeft = false;
        } else if (keyCode == Input.Keys.DPAD_RIGHT) {
            heldRight = false;
        } else if (keyCode == Input.Keys.DPAD_UP) {
            heldUp = false;
        } else if (keyCode == Input.Keys.DPAD_DOWN) {
            heldDown = false;
        } else if (keyCode == Input.Keys.Q) {
            heldQ = false;
        } else if (keyCode == Input.Keys.Z) {
            heldZ = false;
        } else if (keyCode == Input.Keys.SPACE) {
            heldSpace = false;
        }
        return false;
    }

    public boolean isHeldUp() {
        return heldUp;
    }

    public boolean isHeldDown() {
        return heldDown;
    }

    public boolean isHeldLeft() {
        return heldLeft;
    }

    public boolean isHeldRight() {
        return heldRight;
    }

    public boolean isHeldQ() {
        return heldQ;
    }

    public boolean isHeldZ() {
        return heldZ;
    }

    public boolean isHeldSpace() {
        return heldSpace;
    }
}
