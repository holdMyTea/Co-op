package com.forsenboyz.rise42.server.collisions;

import com.forsenboyz.rise42.server.objects.Character;
import com.forsenboyz.rise42.server.objects.Object;
import com.forsenboyz.rise42.server.objects.ObjectHolder;
import com.forsenboyz.rise42.server.objects.projectiles.Projectile;
import com.forsenboyz.rise42.server.parser.ConfigParser;

import java.util.ArrayList;

// just borders yet
public class CollisionDetector {

    private final int BORDER_THICK;

    private final int SOME_GAP_CONST = 4;

    private final int OBJECT_MIN_X;
    private final int OBJECT_MAX_X;
    private final int OBJECT_MIN_Y;
    private final int OBJECT_MAX_Y;

    private ObjectHolder objectHolder;

    private ArrayList<Object> wallBlocks;

    public CollisionDetector(ObjectHolder objectHolder){
        BORDER_THICK = ConfigParser.getBorderThick();
        this.wallBlocks = ConfigParser.getWallBlocks();

        int backgroundSize = ConfigParser.getBackgroundSize();

        this.objectHolder = objectHolder;

        OBJECT_MIN_X = BORDER_THICK;
        OBJECT_MIN_Y = BORDER_THICK;

        OBJECT_MAX_X = backgroundSize - BORDER_THICK;
        OBJECT_MAX_Y = backgroundSize - BORDER_THICK;
    }

    public void moveHero(Character character, int angle, boolean forward){
        character.move(angle,forward);
        if(!isMovedFromBorders(character)){
            moveFromBlocks(character);
        }
    }

    public void moveProjectile(Projectile projectile){
        projectile.move();
        if(!isDestroyedFromBorders(projectile)){
            destroyByBlocks(projectile);
        }
    }

    /**
     Checks whether character is in screen bounds, if not moves it in
     * @param character character to be checked
     * @return whether character was moved back into borders
     */
    private boolean isMovedFromBorders(Character character){
        boolean changed = false;
        if (character.getX() < OBJECT_MIN_X) {
            character.setX(OBJECT_MIN_X);
            changed = true;
        } else if (character.getX() > OBJECT_MAX_X - objectHolder.getMage().getWidth()) {
            character.setX(OBJECT_MAX_X - objectHolder.getMage().getWidth());
            changed = true;
        }

        if (character.getY() < OBJECT_MIN_Y) {
            character.setY(OBJECT_MIN_Y);
            changed = true;
        } else if (character.getY() > OBJECT_MAX_Y - objectHolder.getMage().getHeight()) {
            character.setY(OBJECT_MAX_Y - objectHolder.getMage().getHeight());
            changed = true;
        }

        return changed;
    }

    private boolean isDestroyedFromBorders(Projectile projectile){
        if(projectile.getX() < OBJECT_MIN_X || projectile.getX() > OBJECT_MAX_X - projectile.getWidth()
                || projectile.getY() < OBJECT_MIN_Y || projectile.getY() > OBJECT_MAX_Y - projectile.getHeight()){
            projectile.destroy();
            return true;
        }
        return false;
    }

    private void moveFromBlocks(Object object){
        for(Object wallBlock:wallBlocks){
            Direction direction = wallBlock.checkCollision(object);
            if (direction != Direction.NO){
                System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\tCollision "+ direction +" with block "+wallBlock);
                switch (direction){
                    case TOP_LEFT:
                        object.setY(wallBlock.getY2()+SOME_GAP_CONST);
                        object.setX2(wallBlock.getX()-SOME_GAP_CONST);
                        break;
                    case TOP:
                        object.setY(wallBlock.getY2()+SOME_GAP_CONST);
                        break;
                    case TOP_RIGHT:
                        object.setY(wallBlock.getY2()+SOME_GAP_CONST);
                        object.setX(wallBlock.getX2()+SOME_GAP_CONST);
                        break;
                    case LEFT:
                        object.setX2(wallBlock.getX()-SOME_GAP_CONST);
                        break;
                    case ALL:
                        break;
                    case RIGHT:
                        object.setX(wallBlock.getX2()+SOME_GAP_CONST);
                        break;
                    case BOT_LEFT:
                        object.setY2(wallBlock.getY()-SOME_GAP_CONST);
                        object.setX2(wallBlock.getX()-SOME_GAP_CONST);
                        break;
                    case BOT:
                        object.setY2(wallBlock.getY()-SOME_GAP_CONST);
                        break;
                    case BOT_RIGHT:
                        object.setY2(wallBlock.getY()-SOME_GAP_CONST);
                        object.setX(wallBlock.getX2()+SOME_GAP_CONST);
                        break;
                }
            }
        }
    }

    private void destroyByBlocks(Projectile projectile) {
        for (Object wallBlock : wallBlocks){
            if(wallBlock.checkCollision(projectile) != Direction.NO){
                projectile.destroy();
                return;
            }
        }
    }

}
