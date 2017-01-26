package com.forsenboyz.rise42.server.objects.managers;

import com.forsenboyz.rise42.server.objects.projectiles.Projectile;
import com.forsenboyz.rise42.server.objects.projectiles.StaticTimedProjectile;

public class ProjectileBuilder {

    public final static int FIREBALL = 0;
    public final static int FIREBALL_EXPLOSION = 1;

    public final static int FIREBALL_SIZE = 16;
    public final static int FIREBALL_RANGE = 500;
    public final static int FIREBALL_SPEED = 15;

    public static final int FIREBALL_EXPLOSION_SIZE = 64;
    public static final int FIREBALL_EXPLOSION_TIME = 70;

    public static Projectile makeFireball(float x, float y, int angle) {
        return new Projectile(
                FIREBALL,
                x,
                y,
                FIREBALL_SIZE,
                FIREBALL_SIZE,
                FIREBALL_RANGE,
                FIREBALL_SPEED,
                angle);
    }

    public static Projectile makeFireballExplosion(float centreX, float centreY) {
        return new StaticTimedProjectile(
                FIREBALL_EXPLOSION,
                centreX - FIREBALL_EXPLOSION_SIZE/2,
                centreY - FIREBALL_EXPLOSION_SIZE/2,
                FIREBALL_EXPLOSION_SIZE,
                FIREBALL_EXPLOSION_SIZE,
                FIREBALL_EXPLOSION_TIME);
    }

}
