package com.forsenboyz.rise42.server.objects.managers;

import com.forsenboyz.rise42.server.objects.characters.Character;
import com.forsenboyz.rise42.server.parser.ConfigParser;
import com.forsenboyz.rise42.server.parser.Coordinates;

import java.util.LinkedList;
import java.util.Random;

public class EnemyManager {

    private static final int SPAWN_DELAY = 1600;

    private Coordinates[] spawns;
    private Random random;

    private LinkedList<Character> enemies;

    private long lastSpawned;

    public EnemyManager() {
        this.enemies = new LinkedList<>();
        this.spawns = ConfigParser.getSpawns();
        this.random = new Random(46237526354L);
        lastSpawned = 0;
    }

    public void update(long currentTimeMillis) {
        if (currentTimeMillis - lastSpawned > SPAWN_DELAY) {
            int r = this.random.nextInt(spawns.length);
            this.enemies.add(
                    EnemyBuilder.makeSkeleton(
                            spawns[r].x,
                            spawns[r].y
                    )
            );
        }


    }

    /*public void addEnemy(Character enemy) {
        this.enemies.add(enemy);
    }*/

    public LinkedList<Character> getEnemies() {
        return enemies;
    }
}
