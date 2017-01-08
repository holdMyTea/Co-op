package com.forsenboyz.rise42.server.collisions;

import com.forsenboyz.rise42.server.objects.Hero;
import com.forsenboyz.rise42.server.objects.Object;
import com.forsenboyz.rise42.server.objects.WallBlock;
import com.forsenboyz.rise42.server.parser.ConfigParser;

import java.util.ArrayList;

// just borders yet
public class CollisionDetector {

    private final int BORDER_THICK;

    private final int SOME_GAP_CONST = 4;

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
        System.out.println("initial: "+hero);
        hero.move(angle,forward);
        System.out.println("interm: "+hero);
        if(!isMovedFromBorders(hero)){
            moveFromBlocks(hero);
        }

        System.out.println("final: "+hero);
    }

    /**
     Checks whether hero is in screen bounds, if not moves it in
     * @param hero hero to be checked
     * @return whether hero was moved back into borders
     */
    private boolean isMovedFromBorders(Hero hero){
        boolean changed = false;
        if (hero.getX() < HERO_MIN_X) {
            hero.setX(HERO_MIN_X);
            changed = true;
        } else if (hero.getX() > HERO_MAX_X) {
            hero.setX(HERO_MAX_X);
            changed = true;
        }

        if (hero.getY() < HERO_MIN_Y) {
            hero.setY(HERO_MIN_Y);
            changed = true;
        } else if (hero.getY() > HERO_MAX_Y) {
            hero.setY(HERO_MAX_Y);
            changed = true;
        }

        return changed;
    }

    private void moveFromBlocks(Object object){
        for(WallBlock wallBlock:wallBlocks){
            Direction direction = wallBlock.checkCollision(object);
            if (direction != Direction.NO){
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\tCollision "+ direction +" with "+wallBlock);
                switch (direction){
                    case TOP_LEFT:
                        object.setY(wallBlock.getY2()+SOME_GAP_CONST);
                        object.setX(wallBlock.getX()-SOME_GAP_CONST);
                        break;
                    case TOP:
                        object.setY(wallBlock.getY2()+SOME_GAP_CONST);
                        break;
                    case TOP_RIGHT:
                        object.setY(wallBlock.getY2()+SOME_GAP_CONST);
                        object.setX(wallBlock.getX2()+SOME_GAP_CONST);
                        break;
                    case LEFT:
                        object.setX(wallBlock.getX()-SOME_GAP_CONST);
                        break;
                    case ALL:
                        break;
                    case RIGHT:
                        object.setX(wallBlock.getX2()+SOME_GAP_CONST);
                        break;
                    case BOT_LEFT:
                        object.setY(wallBlock.getY()-SOME_GAP_CONST);
                        object.setX(wallBlock.getX()-SOME_GAP_CONST);
                        break;
                    case BOT:
                        object.setY(wallBlock.getY()-SOME_GAP_CONST);
                        break;
                    case BOT_RIGHT:
                        object.setY(wallBlock.getY()-SOME_GAP_CONST);
                        object.setX(wallBlock.getX2()+SOME_GAP_CONST);
                        break;
                }
            }
        }
    }

}
