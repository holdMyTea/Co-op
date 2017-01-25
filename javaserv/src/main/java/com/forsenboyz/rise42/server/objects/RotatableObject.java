package com.forsenboyz.rise42.server.objects;

public class RotatableObject extends Object {

    protected int angle;

    public RotatableObject(Type type, float x, float y, int width, int height, int angle) {
        super(type, x, y, width, height);
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    /**r
     * changes angle preserving it in [0;360)
     * @param initial initial angle
     * @param delta signed value by which angle will be changed
     * @return angle between 0 and 359
     */
    protected static int convertAngle(int initial, int delta) {
        int check = initial + delta;

        if (check < 0) {
            return 360 + check;
        } else if (check >= 360) {
            return check - 360;
        } else return check;
    }
}
