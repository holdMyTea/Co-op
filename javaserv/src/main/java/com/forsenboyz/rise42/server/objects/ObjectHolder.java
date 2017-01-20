package com.forsenboyz.rise42.server.objects;

import com.forsenboyz.rise42.server.objects.actions.Action;
import com.forsenboyz.rise42.server.objects.projectiles.Projectile;
import com.forsenboyz.rise42.server.objects.projectiles.ProjectileBuilder;
import com.forsenboyz.rise42.server.parser.ConfigParser;

import java.util.ArrayList;

public class ObjectHolder {

    private Character mage;
    private Character war;

    private ArrayList<Projectile> projectiles;

    public ObjectHolder() {
        this.projectiles = new ArrayList<>();

        this.mage = ConfigParser.getMage();
        this.mage.addAction(
                0,
                new Action(
                        (objectHolder) -> {
                            objectHolder.addProjectile(
                                    ProjectileBuilder.makeFireball(
                                            mage.getCentreX()
                                                    -ProjectileBuilder.FIREBALL_SIZE/2
                                                    +1.5f*(mage.getWidth()/2
                                                    *(float)Math.cos(Math.toRadians(mage.getAngle())))
                                            ,
                                            mage.getCentreY()
                                                    -ProjectileBuilder.FIREBALL_SIZE/2
                                                    +1.5f*(mage.getHeight()/2
                                                    *(float)Math.sin(Math.toRadians(mage.getAngle()))),
                                            mage.getAngle()
                                    )
                            );
                        },
                        2000,
                        750)
        );

        this.war = ConfigParser.getWar();
        this.war.addAction(
                0,
                new Action(
                        (objectHolder) -> {

                        },
                        2000,
                        750)
        );
    }

    public void updateProjectiles() {
        for (int i = 0; i < projectiles.size(); i++) {
            if (projectiles.get(i).hasReachedDestination()) {
                projectiles.remove(i);
            } else {
                projectiles.get(i).move();
            }
        }
    }

    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
    }

    public void removeProjectile(Projectile projectile) {
        this.projectiles.remove(projectile);
    }

    public Character getMage() {
        return mage;
    }

    public Character getWar() {
        return war;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }
}
