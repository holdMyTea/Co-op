package com.forsenboyz.rise42.coop.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.forsenboyz.rise42.coop.network.MessageManager;
import com.forsenboyz.rise42.coop.objects.Hero;
import com.forsenboyz.rise42.coop.objects.Object;

public class PlayState extends State {

    public static final int SPEED = 5;

    Hero hero;

    PlayState(MessageManager messageManager, boolean active){
        super(messageManager, active);

        hero = new Hero(50,100);
        objects.add(hero);

        objects.add(0,new Object(new Texture("back.jpg"),0,0));
    }

    @Override
    protected void update(float delta) {
        handleInput();
    }

    private void handleInput(){
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            hero.moveHorizontally(-1*SPEED);
        } else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            hero.moveHorizontally(SPEED);
        } else if(Gdx.input.isKeyPressed(Input.Keys.Z)){   //TODO: i need correctly working ESC button
            messageManager.pause();
        }
    }
}
