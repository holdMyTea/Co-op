package com.forsenboyz.rise42.server.objects.projectiles;

public class ProjectileBuilder {

    public final static int FIREBALL = 0;

    private final static int FIREBALL_SIZE = 16;
    private final static int FIREBALL_RANGE = 150;
    private final static int FIREBALL_SPEED = 20;

    public static Projectile makeFireball(float x, float y, int angle){
        return new Projectile(FIREBALL,x,y,FIREBALL_SIZE,FIREBALL_SIZE,FIREBALL_RANGE,FIREBALL_SPEED,angle);
    }
}
