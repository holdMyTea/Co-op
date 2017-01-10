package com.forsenboyz.rise42.server.objects;

import com.forsenboyz.rise42.server.parser.ConfigParser;

import java.util.ArrayList;

public class ObjectHolder {

    private Hero mage;
    private Hero war;

    private ArrayList<Projectile> projectiles;

    public ObjectHolder(){
        this.projectiles = new ArrayList<>();

        this.mage = ConfigParser.getMage();
        this.war = ConfigParser.getWar();
    }

    public void addProjectile(Projectile projectile){
        this.projectiles.add(projectile);
    }

    public void removeProjectile(Projectile projectile){
        this.projectiles.remove(projectile);
    }

    public Hero getMage() {
        return mage;
    }

    public Hero getWar() {
        return war;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }
}
