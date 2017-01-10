package com.forsenboyz.rise42.server.cycle;

import com.forsenboyz.rise42.server.collisions.CollisionDetector;
import com.forsenboyz.rise42.server.message.IncomeProcessor;
import com.forsenboyz.rise42.server.message.OutcomeProcessor;
import com.forsenboyz.rise42.server.objects.ObjectHolder;

public class MainCycle {

    private final int INTERVAL_WAIT = 1000 / 40;
    private long lastCycle;

    private ObjectHolder objectHolder;
    private CollisionDetector collisionDetector;
    private IncomeProcessor incomeProcessor;
    private OutcomeProcessor outcomeProcessor;

    public MainCycle(){
        this.lastCycle = System.currentTimeMillis();

        this.objectHolder = new ObjectHolder();
        this.incomeProcessor = new IncomeProcessor(this);
    }

    public void runCycle(){
        Thread cycle = new Thread(
                () -> {
                    while(true){
                        if(System.currentTimeMillis() - lastCycle > INTERVAL_WAIT){







                            lastCycle = System.currentTimeMillis();
                        }


                    }
                }
        );
        cycle.setDaemon(true);
        cycle.start();
    }

    public void pause(){

    }

    public void play(){

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
