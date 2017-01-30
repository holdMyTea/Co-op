package com.forsenboyz.rise42.server.objects.characters;

import com.forsenboyz.rise42.server.objects.Type;
import com.forsenboyz.rise42.server.objects.Object;
import com.forsenboyz.rise42.server.parser.Coordinates;

import java.util.ArrayDeque;


public class NPC extends Character {

    private Object target;
    private ArrayDeque<Coordinates> path;

    public NPC(float moveSpeed, int maxHP, float x, float y, int angle, int width, int height) {
        super(Type.Enemy, moveSpeed, maxHP, x, y, angle, width, height);
        this.path = new ArrayDeque<>();
    }

    public void chooseTarget(Hero first, Hero second) {
        System.out.println("Choosing target");
        if (distanceTo(first) < distanceTo(second)) {
            this.target = first;
        } else {
            this.target = second;
        }
    }

    public void moveAlongPath(){
        Coordinates nextPoint = path.peek();
        this.angle = angleTo(nextPoint);
        move();
        System.out.println("Moving to: "+nextPoint);
        if (distanceTo(nextPoint) < this.getWidth()/2){
            this.path.poll();
        }
    }

    private void move() {
        this.x += this.moveSpeed * Math.cos(Math.toRadians(angle));
        this.y += this.moveSpeed * Math.sin(Math.toRadians(angle));
    }

    public float distanceTo(Object target) {
        return this.distanceTo(target.getCoordinates());
    }

    public float distanceTo(Coordinates coordinates){
        return (float) Math.sqrt(
                Math.pow(this.getCentreX() - coordinates.x, 2)
                        + Math.pow(this.getCentreY() - coordinates.y, 2)
        );
    }

    public int angleTo(Object object) {
        return this.angleTo(object.getCoordinates());
    }

    public int angleTo(Coordinates point){
        int angle = (int) Math.toDegrees(
                Math.atan2(
                    point.y - y, point.x - x
                )
        );

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }

    public void addToPath(Coordinates point){
        System.out.println("Kappa "+point);
        this.path.add(point);
    }

    public boolean hasCompletedPath(){
        return path.isEmpty();
    }

    public Object getTarget() {
        return target;
    }
}
