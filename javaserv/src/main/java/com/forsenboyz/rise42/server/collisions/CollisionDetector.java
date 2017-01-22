package com.forsenboyz.rise42.server.collisions;

import com.forsenboyz.rise42.server.objects.Character;
import com.forsenboyz.rise42.server.objects.Object;
import com.forsenboyz.rise42.server.objects.ObjectHolder;
import com.forsenboyz.rise42.server.objects.RotatableObject;
import com.forsenboyz.rise42.server.objects.projectiles.Projectile;
import com.forsenboyz.rise42.server.parser.ConfigParser;

import java.util.ArrayList;

// just borders yet
public class CollisionDetector {

    public static final int SOME_GAP_CONST = 4;

    private ObjectHolder objectHolder;

    private Object[] borderBlocks;
    private ArrayList<Object> wallBlocks;

    public CollisionDetector(ObjectHolder objectHolder) {
        borderBlocks = ConfigParser.getBorderBlocks();
        this.wallBlocks = ConfigParser.getWallBlocks();

        this.objectHolder = objectHolder;
    }

    /**
     * Checks whether object collides with other objects, should be called after object.move(...)
     *
     * @param object object to check collisions with
     */
    public void check(RotatableObject object) {
        checkBorderCollision(object);
        checkBlockCollision(object);
    }

    private void checkBorderCollision(RotatableObject object) {
        for (Object borderBlock : borderBlocks) {
            borderBlock.checkCollision(object);
        }
    }

    private void checkBlockCollision(Object object) {
        for (Object wallBlock : wallBlocks) {
            wallBlock.checkCollision(object);
        }
    }

}
