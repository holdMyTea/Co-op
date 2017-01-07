package com.forsenboyz.rise42.server.collisions;

import com.forsenboyz.rise42.server.objects.Hero;
import com.forsenboyz.rise42.server.objects.Object;
import com.forsenboyz.rise42.server.objects.WallBlock;
import com.forsenboyz.rise42.server.parser.ConfigParser;

import java.util.ArrayList;

// just borders yet
public class CollisionDetector {

    private final int BORDER_THICK;

    private final int HERO_MIN_X;
    private final int HERO_MAX_X;
    private final int HERO_MIN_Y;
    private final int HERO_MAX_Y;

    private Hero mage;
    private Hero war;

    private ArrayList<WallBlock> wallBlocks;

    public CollisionDetector(Hero mage, Hero war){
        BORDER_THICK = ConfigParser.getBorderThick();
        this.wallBlocks = ConfigParser.getWallBlocks();

        int backgroundSize = ConfigParser.getBackgroundSize();

        this.mage = mage;
        this.war = war;

        HERO_MIN_X = BORDER_THICK;
        HERO_MIN_Y = BORDER_THICK;

        HERO_MAX_X = backgroundSize - BORDER_THICK - mage.getWidth();
        HERO_MAX_Y = backgroundSize - BORDER_THICK - mage.getHeight();
    }

    public void moveHero(Hero hero, int angle, boolean forward){
        hero.move(angle,forward);
        if(checkHeroInBorders(hero)){
            return;
        } else{
            if(checkBlockCollision(hero)){
                hero.move(angle, !forward);
            }
        }
    }

    /**
     Checks whether hero is in screen bounds, if not moves it in
     * @param hero hero to be checked
     * @return whether hero was moved back into borders
     */
    private boolean checkHeroInBorders(Hero hero){
        /*  Tomorrow:
            this method is completely wrong,
            collision,
            too much messages to client
        */
        System.out.println("initial: "+hero);
        if (hero.getX() < HERO_MIN_X) {
            hero.setX(HERO_MIN_X);
        } else if (hero.getX() > HERO_MAX_X) {
            hero.setX(HERO_MAX_X);
        } else return false;

        if (hero.getY() < HERO_MIN_Y) {
            hero.setY(HERO_MIN_Y);
        } else if (hero.getY() > HERO_MAX_Y) {
            hero.setY(HERO_MAX_Y);
        } else return false;

        System.out.println("final: "+hero);
        return true;
    }

    private boolean checkBlockCollision(Object object){
        for(WallBlock wallBlock:wallBlocks){
            if (wallBlock.checkCollision(object) != Collision.NO){
                System.out.println("Collision with "+wallBlock);
                return true;
            }
        }
        return false;
    }

}
