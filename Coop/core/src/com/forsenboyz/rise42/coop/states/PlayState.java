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
    Hero anotherHero;

    PlayState(MessageManager messageManager, boolean active){
        super(messageManager, active);

        hero = new Hero(50,100);
        objects.add(hero);

        anotherHero = new Hero("vampire.png",400,100);
        objects.add(anotherHero);

        objects.add(0,new Object("back.jpg",0,0));
    }

    public void moveHero(int positionX){
        this.hero.moveHorizontally(positionX);
    }

    public void moveAnotherHero(int positionX) {this.anotherHero.moveHorizontally(positionX);}

    @Override
    protected void update(float delta) {
        handleInput();
    }

    private void handleInput(){
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            messageManager.move(false);
        } else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            messageManager.move(true);
        } else if(Gdx.input.isKeyPressed(Input.Keys.Z)){   //TODO: i need correctly working ESC button
            messageManager.pause();
        }
    }
}
