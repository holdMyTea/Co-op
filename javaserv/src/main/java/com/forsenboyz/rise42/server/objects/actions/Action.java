package com.forsenboyz.rise42.server.objects.actions;


public class Action {

    private long COOLDOWN;
    private long CAST_TIME;

    private Castable castable;

    private long startedCasting;
    private long lastUsed;

    private boolean casting;
    private boolean ready;

    public Action(Castable castable, long cooldown, long castTime) {
        this.castable = castable;

        this.COOLDOWN = cooldown;
        this.CAST_TIME = castTime;

        this.startedCasting = 0;
        this.lastUsed = 0;

        this.casting = false;
        this.ready = false;
    }

    /**
     * Starts count down for cast time, if cooldown is over
     * @param currentTimeMillis time according which timings are updated
     * @return  whether casting was started
     */
    public boolean startCasting(long currentTimeMillis){
        if(!casting && currentTimeMillis - lastUsed > COOLDOWN){
            this.startedCasting = currentTimeMillis;
            this.casting = true;
            return true;
        }
        return false;
    }


    /**
     * Checks whether cast time has expired, if so changes ready status
     * @param currentTimeMillis time according which timings are updated
     */
    public void update(long currentTimeMillis){
        if (casting && currentTimeMillis - startedCasting > CAST_TIME){
            this.ready = true;
        }
    }


    /**
     * Resets statuses and returns corresponding Castable in advance, if ready == true,
     * does nothing and returns null otherwise
     * @param currentTimeMillis time according which timings are updated
     * @return corresponding Castable or null
     */
    public Castable cast(long currentTimeMillis){
        System.out.println("Castcastcastcastcastcastcastcastcastcastcastcast");
        if(this.ready) {
            this.ready = false;
            this.casting = false;

            this.lastUsed = currentTimeMillis;

            return this.castable;
        } else return null;
    }

    public boolean isCasting() {
        return casting;
    }

    public boolean isReady(){
        return this.ready;
    }
}
