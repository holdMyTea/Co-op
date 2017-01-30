package com.forsenboyz.rise42.server.cycle;

import com.forsenboyz.rise42.server.collisions.CollisionDetector;
import com.forsenboyz.rise42.server.collisions.PathMaker;
import com.forsenboyz.rise42.server.message.IncomeProcessor;
import com.forsenboyz.rise42.server.message.OutcomeProcessor;
import com.forsenboyz.rise42.server.network.Server;
import com.forsenboyz.rise42.server.objects.characters.Hero;
import com.forsenboyz.rise42.server.objects.managers.EnemyManager;
import com.forsenboyz.rise42.server.objects.managers.ObjectHolder;
import com.forsenboyz.rise42.server.objects.actions.Castable;
import com.forsenboyz.rise42.server.objects.managers.ProjectileManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainCycle {

    private final int INTERVAL_WAIT = 30;
    private long currentCycle;

    private AtomicBoolean paused;

    private ObjectHolder objectHolder;
    private ProjectileManager projectileManager;
    private EnemyManager enemyManager;

    private CollisionDetector collisionDetector;
    private IncomeProcessor incomeProcessor;
    private OutcomeProcessor outcomeProcessor;

    private Hero mage;
    private Hero war;

    public MainCycle(Server server) {
        System.out.println("Main init");
        this.currentCycle = System.currentTimeMillis();
        this.paused = new AtomicBoolean(true);

        this.objectHolder = new ObjectHolder();
        this.projectileManager = this.objectHolder.getProjectileManager();
        this.enemyManager = this.objectHolder.getEnemyManager();

        this.mage = objectHolder.getMage();
        this.war = objectHolder.getWar();

        this.incomeProcessor = new IncomeProcessor(this);
        this.collisionDetector = new CollisionDetector(this.objectHolder);
        this.outcomeProcessor = new OutcomeProcessor(this.objectHolder, server);

        outcomeProcessor.makeMessage();
    }

    public void runCycle() {
        Thread cycle = new Thread(
                () -> {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss.SSS");
                    while (true) {
                        if (!paused.get()) {
                            currentCycle = System.currentTimeMillis();

                            for(Castable castable : mage.update(currentCycle)){
                                castable.onCast(this.projectileManager);
                            }

                            for(Castable castable : war.update(currentCycle)){
                                castable.onCast(this.projectileManager);
                            }

                            projectileManager.getProjectiles().forEach(
                                    (projectile -> {
                                        projectile.move();
                                        this.collisionDetector.check(projectile);
                                    })
                            );
                            projectileManager.removeDestroyed();

                            enemyManager.getEnemies().forEach(
                                    (enemy) -> {
                                        if(enemy.hasCompletedPath()){
                                            System.out.println("Path making");
                                            enemy.chooseTarget(mage, war);
                                            PathMaker.makePath(
                                                    enemy,
                                                    collisionDetector.getWallBlocks()
                                            );
                                        }
                                        enemy.moveAlongPath();
                                        this.collisionDetector.check(enemy);
                                    }
                            );
                            enemyManager.removeDead();
                            enemyManager.spawn(currentCycle);

                            outcomeProcessor.makeMessage();
                            System.out.println("cycle: " + dateFormat.format(new Date()));
                        }

                        try {
                            synchronized (this) {
                                this.wait(INTERVAL_WAIT);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );
        cycle.start();
    }

    public void pause() {
        paused.set(true);
        outcomeProcessor.makePauseMessage();
    }

    public void play() {
        paused.set(false);
        outcomeProcessor.makePlayMessage();
    }

    public void moveMage(int angle, boolean forward) {
        mage.move(angle, forward);
        collisionDetector.check(mage);
    }

    public void moveWar(int angle, boolean forward) {
        war.move(angle, forward);
        collisionDetector.check(war);
    }

    public void rotateMage(int angle) {
        mage.setAngle(angle);
    }

    public void rotateWar(int angle) {
        war.setAngle(angle);
    }

    public void actionMage(int index, int angle) {
        rotateMage(angle);
        mage.startAction(index, System.currentTimeMillis());
    }

    public void actionWar(int index, int angle) {
        rotateWar(angle);
        war.startAction(index, System.currentTimeMillis());
    }

    public IncomeProcessor getIncomeProcessor() {
        return incomeProcessor;
    }

}
