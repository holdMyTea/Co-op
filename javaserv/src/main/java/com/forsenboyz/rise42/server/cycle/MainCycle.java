package com.forsenboyz.rise42.server.cycle;

import com.forsenboyz.rise42.server.collisions.CollisionDetector;
import com.forsenboyz.rise42.server.message.IncomeProcessor;
import com.forsenboyz.rise42.server.message.OutcomeProcessor;
import com.forsenboyz.rise42.server.objects.ObjectHolder;

public class MainCycle {

    private final int INTERVAL_WAIT = 1000 / 40;
    private long lastCycle;

    private boolean paused;

    private ObjectHolder objectHolder;
    private CollisionDetector collisionDetector;
    private IncomeProcessor incomeProcessor;
    private OutcomeProcessor outcomeProcessor;

    public MainCycle(){
        System.out.println("Main init");
        this.lastCycle = System.currentTimeMillis();
        this.paused = true;

        this.objectHolder = new ObjectHolder();
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
                            outcomeProcessor.makeMessage();
                            System.out.println("cycle");
                        }
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
        collisionDetector.moveHero(objectHolder.getMage(), angle, forward);
    }

    public void moveWar(int angle, boolean forward){
        collisionDetector.moveHero(objectHolder.getWar(), angle, forward);
    }

    public void rotateMage(int angle){
        objectHolder.getMage().setAngle(angle);
    }

    public void rotateWar(int angle){
        objectHolder.getWar().setAngle(angle);
    }

    public void actionMage(int index, int angle){

    }

    public void actionWar(int index, int angle){

    }

    public IncomeProcessor getIncomeProcessor() {
        return incomeProcessor;
    }

    public OutcomeProcessor getOutcomeProcessor() {
        return outcomeProcessor;
    }
}
