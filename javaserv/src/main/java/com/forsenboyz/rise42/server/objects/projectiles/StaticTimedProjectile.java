package com.forsenboyz.rise42.server.objects.projectiles;

import com.forsenboyz.rise42.server.collisions.Direction;
import com.forsenboyz.rise42.server.objects.Object;

public class StaticTimedProjectile extends TimedProjectile {

    public StaticTimedProjectile(int type, float x, float y, int width, int height, int maxCycles) {
        super(type, x, y, width, height, 1, 0, 0, maxCycles);
    }

    @Override
    public void move() {
        this.currentTime++;
    }

    @Override
    protected int onCollisionCheck(Object other) {
        return Direction.NO;
    }

    @Override
    public boolean isDestroyed() {
        return currentTime > maxTime;
    }
}
