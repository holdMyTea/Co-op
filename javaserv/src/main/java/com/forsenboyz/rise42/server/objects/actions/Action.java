package com.forsenboyz.rise42.server.objects.actions;


public class Action {

    private final long COOLDOWN;
    private final long CAST_TIME;

    private long lastUsed;
    private boolean active;


    public Action(long cooldown, long castTime) {
        this.COOLDOWN = cooldown;
        this.CAST_TIME = castTime;

        this.lastUsed = 0;
        this.active = false;
    }

    public boolean activate(long currentTimeMillis){
        if((currentTimeMillis - (lastUsed + CAST_TIME)) > COOLDOWN){
            lastUsed = currentTimeMillis;
            active = true;
            return true;
        }
        return false;
    }

    public void update(long currentTimeMillis){
        if (currentTimeMillis - lastUsed > CAST_TIME){
            active = false;
        }
    }

    public boolean isActive() {
        return active;
    }
}
