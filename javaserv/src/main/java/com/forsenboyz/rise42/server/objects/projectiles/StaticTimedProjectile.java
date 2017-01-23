package com.forsenboyz.rise42.server.objects.projectiles;

import com.forsenboyz.rise42.server.objects.Object;

public class StaticTimedProjectile extends TimedProjectile {

    StaticTimedProjectile(int type, float x, float y, int width, int height, int maxCycles) {
        super(type, x, y, width, height, 1, 0, 0, maxCycles);
    }

    @Override
    public void move() {
        this.currentTime++;
    }

    @Override
    public void onCollided(Object other, int direction) {
        // do nothing
    }

    @Override
    boolean isDestroyed() {
        return currentTime > maxTime;
    }
}
