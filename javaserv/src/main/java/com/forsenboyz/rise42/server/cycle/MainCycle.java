package com.forsenboyz.rise42.server.cycle;

import com.forsenboyz.rise42.server.collisions.CollisionDetector;
import com.forsenboyz.rise42.server.message.IncomeProcessor;
import com.forsenboyz.rise42.server.message.OutcomeProcessor;
import com.forsenboyz.rise42.server.objects.Character;
import com.forsenboyz.rise42.server.objects.ObjectHolder;

public class MainCycle {

    private final int INTERVAL_WAIT = 1000 / 40;
    private long lastCycle;

    private boolean paused;

    private ObjectHolder objectHolder;
    private CollisionDetector collisionDetector;
    private IncomeProcessor incomeProcessor;
    private OutcomeProcessor outcomeProcessor;

    private Character mage;
    private Character war;

    public MainCycle(){
        System.out.println("Main init");
        this.lastCycle = System.currentTimeMillis();
        this.paused = true;

        this.objectHolder = new ObjectHolder();

        this.mage = objectHolder.getMage();
        this.war = objectHolder.getWar();

        this.incomeProcessor = new IncomeProcessor(this);
        this.collisionDetector = new CollisionDetector(this.objectHolder);
        this.outcomeProcessor = new OutcomeProcessor(this.objectHolder);

        outcomeProcessor.makeMessage();
        System.out.println(outcomeProcessor.getMessage());
    }

    public void runCycle(){
        Thread cycle = new Thread(
                () -> {
                    while(true){
                        if(isPaused()) continue;

                        if(System.currentTimeMillis() - lastCycle > INTERVAL_WAIT){
                            lastCycle = System.currentTimeMillis();
                            this.mage.update(lastCycle);
                            this.war.update(lastCycle);
                            outcomeProcessor.makeMessage();
                            System.out.println("cycle");
                        }

                        /* ended here:
                            war is horribly lagging,
                            projectiles
                         */
                    }
                }
        );
        cycle.start();
    }

    public synchronized void pause(){
        paused = true;
        outcomeProcessor.makePauseMessage();
    }

    public synchronized void play(){
        paused = false;
        outcomeProcessor.makePlayMessage();
    }

    private synchronized boolean isPaused(){
        return paused;
    }

    public void moveMage(int angle, boolean forward){
        collisionDetector.moveHero(mage, angle, forward);
    }

    public void moveWar(int angle, boolean forward){
        collisionDetector.moveHero(war, angle, forward);
    }

    public void rotateMage(int angle){
        mage.setAngle(angle);
    }

    public void rotateWar(int angle){
        war.setAngle(angle);
    }

    public void actionMage(int index, int angle){
        rotateMage(angle);
        mage.activateAction(index, System.currentTimeMillis());
    }

    public void actionWar(int index, int angle){
        rotateWar(angle);
        war.activateAction(index, System.currentTimeMillis());
    }

    public IncomeProcessor getIncomeProcessor() {
        return incomeProcessor;
    }

    public OutcomeProcessor getOutcomeProcessor() {
        return outcomeProcessor;
    }
}
