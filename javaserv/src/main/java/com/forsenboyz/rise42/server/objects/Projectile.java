package com.forsenboyz.rise42.server.objects;

public class Projectile extends Object {

    private final int maxMovementRange;
    private final int moveSpeed;

    private int movementAngle;
    private int currentRangeMoved;

    public Projectile(float x, float y, int width, int height,
                      int maxMovementRange, int moveSpeed, int movementAngle, int currentRangeMoved) {
        super(x, y, width, height);
        this.maxMovementRange = maxMovementRange;
        this.moveSpeed = moveSpeed;
        this.movementAngle = movementAngle;
        this.currentRangeMoved = currentRangeMoved;
    }

    public void move(){
        this.x += this.moveSpeed * Math.cos(Math.toRadians(movementAngle));
        this.y += this.moveSpeed * Math.sin(Math.toRadians(movementAngle));
        currentRangeMoved += moveSpeed;
    }

    public boolean hasReachedDestination(){
        return currentRangeMoved - maxMovementRange > 0;
    }
}
