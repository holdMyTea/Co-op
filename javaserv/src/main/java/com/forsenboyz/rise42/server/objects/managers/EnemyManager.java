package com.forsenboyz.rise42.server.objects.managers;

import com.forsenboyz.rise42.server.objects.characters.NPC;
import com.forsenboyz.rise42.server.parser.ConfigParser;
import com.forsenboyz.rise42.server.parser.Coordinates;

import java.util.LinkedList;
import java.util.Random;

public class EnemyManager {

    private static final int MAX_ENEMIES = 1;

    private static final int SPAWN_DELAY = 2500;

    private Coordinates[] spawns;
    private Random random;

    private LinkedList<NPC> enemies;

    private long lastSpawned;

    public EnemyManager() {
        this.enemies = new LinkedList<>();
        this.spawns = ConfigParser.getSpawns();
        this.random = new Random(46237526354L);
        lastSpawned = 0;
    }

    public void removeDead() {
        for (int i = 0; i < enemies.size(); i++) {
            if(enemies.get(i).isDead()){
                enemies.remove(i);
            }
        }
    }

    public void spawn(long currentTimeMillis){
        if ((currentTimeMillis - lastSpawned > SPAWN_DELAY) && (enemies.size() < MAX_ENEMIES)) {
            int r = this.random.nextInt(spawns.length);
            this.enemies.add(
                    EnemyBuilder.makeSkeleton(
                            spawns[1].x,
                            spawns[1].y
                    )
            );
        }
    }

    public LinkedList<NPC> getEnemies() {
        return enemies;
    }
}
