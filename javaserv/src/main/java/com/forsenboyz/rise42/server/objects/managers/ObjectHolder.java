package com.forsenboyz.rise42.server.objects.managers;

import com.forsenboyz.rise42.server.objects.characters.Character;
import com.forsenboyz.rise42.server.objects.actions.Action;
import com.forsenboyz.rise42.server.objects.characters.Hero;
import com.forsenboyz.rise42.server.parser.ConfigParser;

public class ObjectHolder {

    private Hero mage;
    private Hero war;

    private ProjectileManager projectileManager;

    public ObjectHolder() {
        this.projectileManager = new ProjectileManager();

        this.mage = ConfigParser.getMage();
        this.mage.addAction(
                0,
                new Action(
                        (projectileManager) -> {
                            projectileManager.addProjectile(
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

    public Hero getMage() {
        return mage;
    }

    public Hero getWar() {
        return war;
    }

    public ProjectileManager getProjectileManager() {
        return projectileManager;
    }
}
