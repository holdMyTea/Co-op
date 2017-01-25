package com.forsenboyz.rise42.server.objects.projectiles;

import java.util.ArrayList;

public class ProjectileManager {

    private ArrayList<Projectile> projectiles;

    public ProjectileManager(){
        this.projectiles = new ArrayList<>();
    }

    public void removeDestroyed(){
        for (int i = projectiles.size()-1; i >= 0 ; i--) {
            Projectile p = projectiles.get(i);
            if (p.isDestroyed()) {
                this.handleRemoves(p);
                projectiles.remove(i);
            }
        }
    }

    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    private void handleRemoves(Projectile projectile){
        if(projectile.getProjectileType() == ProjectileBuilder.FIREBALL){
            addProjectile(
                    ProjectileBuilder.makeFireballExplosion(
                            projectile.getX(),
                            projectile.getY()
                    )
            );
        }
    }
}
