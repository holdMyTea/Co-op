package com.forsenboyz.rise42.server.objects;

public class Hero extends Object {

    private float moveSpeed;

    private int angle;

    public Hero(float moveSpeed, float x, float y, int angle, int width, int height) {
        super(x,y,width,height);
        this.moveSpeed = moveSpeed;
        this.angle = angle;
    }

    public void move(int angle, boolean forward){
        if(forward){
            this.x += this.moveSpeed * Math.cos(Math.toRadians(angle));
            this.y += this.moveSpeed * Math.sin(Math.toRadians(angle));
        } else{
            int reversedAngle = convertAngle(angle, 180);
            this.x += this.moveSpeed * Math.cos(Math.toRadians(reversedAngle));
            this.y += this.moveSpeed * Math.sin(Math.toRadians(reversedAngle));
        }
        this.angle = angle;
    }

    public void rotate(int angle){
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }

    /**
     * changes angle preserving it in [0;360)
     * @param initial initial angle
     * @param delta signed value by which angle will be changed
     * @return angle between 0 and 359
     */
    private static int convertAngle(int initial, int delta) {
        int check = initial + delta;

        if (check < 0) {
            return 360 + check;
        } else if (check >= 360) {
            return check - 360;
        } else return check;
    }

    @Override
    public String toString() {
        return "x:{"+this.x+"}, y:{"+this.y+"}, angle:{"+this.angle+"};";
    }
}
