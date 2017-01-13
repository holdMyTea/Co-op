package com.forsenboyz.rise42.server.objects;

import com.forsenboyz.rise42.server.objects.actions.Action;
import com.forsenboyz.rise42.server.objects.projectiles.Projectile;
import com.forsenboyz.rise42.server.parser.ConfigParser;

import java.util.ArrayList;

public class ObjectHolder {

    private Character mage;
    private Character war;

    private ArrayList<Projectile> projectiles;

    public ObjectHolder(){
        this.projectiles = new ArrayList<>();

        this.mage = ConfigParser.getMage();
        this.mage.addAction(
                0,
                new Action(2000,750)
        );

        this.war = ConfigParser.getWar();
        this.war.addAction(
                0,
                new Action(2000, 750)
        );
    }

    public void addProjectile(Projectile projectile){
        this.projectiles.add(projectile);
    }

    public void removeProjectile(Projectile projectile){
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
