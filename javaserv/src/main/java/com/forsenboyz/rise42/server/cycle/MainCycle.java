package com.forsenboyz.rise42.server.cycle;

import com.forsenboyz.rise42.server.collisions.CollisionDetector;
import com.forsenboyz.rise42.server.message.IncomeProcessor;
import com.forsenboyz.rise42.server.message.OutcomeProcessor;
import com.forsenboyz.rise42.server.network.Server;
import com.forsenboyz.rise42.server.objects.Character;
import com.forsenboyz.rise42.server.objects.ObjectHolder;
import com.forsenboyz.rise42.server.objects.actions.Action;
import com.forsenboyz.rise42.server.objects.actions.Castable;
import com.forsenboyz.rise42.server.objects.projectiles.ProjectileBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainCycle {

    private final int INTERVAL_WAIT = 40;
    private long lastCycle;

    private AtomicBoolean paused;

    private ObjectHolder objectHolder;
    private CollisionDetector collisionDetector;
    private IncomeProcessor incomeProcessor;
    private OutcomeProcessor outcomeProcessor;

    private Character mage;
    private Character war;

    public MainCycle(Server server) {
        System.out.println("Main init");
        this.lastCycle = System.currentTimeMillis();
        this.paused = new AtomicBoolean(true);

        this.objectHolder = new ObjectHolder();

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
                            lastCycle = System.currentTimeMillis();

                            /*
                                ended here:
                                finish actions,
                                separate from ObjectHandler ProjectileManager,
                                make at least smth work
                             */

                            for(Castable castable : mage.update(lastCycle)){
                                castable.onCast(this.objectHolder);
                            }

                            for(Castable castable : war.update(lastCycle)){
                                castable.onCast(this.objectHolder);
                            }

                            objectHolder.updateProjectiles();

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
        collisionDetector.moveHero(mage, angle, forward);
    }

    public void moveWar(int angle, boolean forward) {
        collisionDetector.moveHero(war, angle, forward);
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

    public boolean isRunning() {
        return !this.paused.get();
    }
}
