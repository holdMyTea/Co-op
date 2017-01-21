package com.forsenboyz.rise42.server.objects.projectiles;

import java.util.ArrayList;

public class ProjectileManager {

    private ArrayList<Projectile> projectiles;

    public ProjectileManager(){
        this.projectiles = new ArrayList<>();
    }

    public void removeDestroyed(){
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = projectiles.get(i);
            if (p.isDestroyed()) {
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
}
