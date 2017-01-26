package com.forsenboyz.rise42.server.objects.projectiles;

import com.forsenboyz.rise42.server.collisions.Direction;
import com.forsenboyz.rise42.server.objects.Object;
import com.forsenboyz.rise42.server.objects.RotatableObject;
import com.forsenboyz.rise42.server.objects.Type;
import com.google.gson.JsonObject;

import static com.forsenboyz.rise42.server.message.JsonProperties.*;

/**
 * Class to represent various projectiles. Gets destroyed by collision or by reaching max travel distance.
 */
public class Projectile extends RotatableObject {

    private final int projectileType;

    private final int maxMovementRange;
    private final int moveSpeed;

    private int movementAngle;
    private int currentRangeMoved;

    private boolean destroyed;

    public Projectile(int projectileType, float x, float y, int width, int height,
               int maxMovementRange, int moveSpeed, int movementAngle) {
        super(Type.Projectile, x, y, width, height, movementAngle);
        this.projectileType = projectileType;
        this.maxMovementRange = maxMovementRange;
        this.moveSpeed = moveSpeed;
        this.movementAngle = movementAngle;
        this.currentRangeMoved = 0;
        this.destroyed = false;
    }

    public void move(){
        this.x += this.moveSpeed * Math.cos(Math.toRadians(movementAngle));
        this.y += this.moveSpeed * Math.sin(Math.toRadians(movementAngle));
        currentRangeMoved += moveSpeed;
    }

    @Override
    public boolean onCollided(Object other, int direction) {
        if(direction != Direction.NO) {
            this.destroyed = true;
        }
        return false;
    }

    /**
     * @return true, if projectile was destroyed by collision or range expiration
     */
    public boolean isDestroyed() {
        return destroyed || currentRangeMoved >= maxMovementRange;
    }

    public int getProjectileType() {
        return projectileType;
    }

    public JsonObject toJson(){
        JsonObject object = new JsonObject();
        object.addProperty(X, this.x);
        object.addProperty(Y, this.y);
        object.addProperty(ANGLE, this.angle);
        object.addProperty(TYPE, this.projectileType);
        return object;
    }
}
