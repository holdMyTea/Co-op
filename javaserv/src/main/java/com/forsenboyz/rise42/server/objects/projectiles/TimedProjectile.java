package com.forsenboyz.rise42.server.objects.projectiles;

public class TimedProjectile extends Projectile {

    protected int currentTime;
    protected int maxTime;

    TimedProjectile(int type, float x, float y, int width, int height,
                    int maxMovementRange, int moveSpeed, int movementAngle, int maxCycles) {
        super(type, x, y, width, height, maxMovementRange, moveSpeed, movementAngle);
        this.maxTime = maxCycles;
        this.currentTime = 0;
    }

    @Override
    public void move() {
        super.move();
        this.currentTime++;
    }


    /**
     * @return true, if projectile was destroyed by collision or expiration of distance or time
     */
    @Override
    boolean isDestroyed() {
        return super.isDestroyed() || currentTime >= maxTime;
    }
}
