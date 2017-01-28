package com.forsenboyz.rise42.server.collisions;

import com.forsenboyz.rise42.server.objects.characters.Character;
import com.forsenboyz.rise42.server.objects.Object;
import com.forsenboyz.rise42.server.objects.managers.ObjectHolder;
import com.forsenboyz.rise42.server.objects.projectiles.Projectile;
import com.forsenboyz.rise42.server.objects.managers.ProjectileManager;
import com.forsenboyz.rise42.server.parser.ConfigParser;
import com.forsenboyz.rise42.server.parser.Coordinates;

import java.util.ArrayList;

// just borders yet
public class CollisionDetector {

    public static final int SOME_GAP_CONST = 4;

    private ObjectHolder objectHolder;

    private Object[] borderBlocks;
    private ArrayList<Object> wallBlocks;

    private ProjectileManager projectileManager;

    public CollisionDetector(ObjectHolder objectHolder) {
        borderBlocks = ConfigParser.getBorderBlocks();
        this.wallBlocks = ConfigParser.getWallBlocks();

        this.projectileManager = objectHolder.getProjectileManager();

        this.objectHolder = objectHolder;
    }

    public void check(Projectile projectile) {
        checkBlockCollision(projectile);
        checkBorderCollision(projectile);
        objectHolder.getMage().checkCollision(projectile);
        objectHolder.getWar().checkCollision(projectile);
    }

    public void check(Character character) {
        checkBorderCollision(character);
        checkBlockCollision(character);
        checkProjectileCollision(character);
    }

    private void checkBorderCollision(Object object) {
        for (Object borderBlock : borderBlocks) {
            borderBlock.checkCollision(object);
        }
    }

    private void checkBlockCollision(Object object) {
        for (Object wallBlock : wallBlocks) {
            wallBlock.checkCollision(object);
        }
    }

    private void checkProjectileCollision(Object object) {
        for (Projectile projectile : projectileManager.getProjectiles()) {
            projectile.checkCollision(object);
        }
    }

    public ArrayList<Object> getBlockCollidedWithLine(Object start, Object end){
        return this.getBlocksCollidedWithLine(
                start.getCoordinates(),
                end.getCoordinates()
        );
    }

    public ArrayList<Object> getBlocksCollidedWithLine(Coordinates start, Coordinates end){
        ArrayList<Object> blocks = new ArrayList<>();

        for (Object o : this.wallBlocks){

            // checking whether obj is in between two points by x
            if((o.getCentreX() < end.x && o.getCentreX() > start.x)
                            || (o.getCentreX() > end.x && o.getCentreX() < start.x)){

                // checking whether obj is in between two points by y
                if((o.getCentreY() < end.y && o.getCentreY() > start.y)
                        || (o.getCentreY() > end.y && o.getCentreY() < start.y)){
                    blocks.add(o);
                }
            }

        }

        return blocks;
    }

    public ArrayList<Object> getWallBlocks() {
        return wallBlocks;
    }
}
