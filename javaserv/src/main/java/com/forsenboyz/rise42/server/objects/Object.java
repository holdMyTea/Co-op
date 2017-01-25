package com.forsenboyz.rise42.server.objects;

import com.forsenboyz.rise42.server.collisions.Direction;

public class Object {

    private Type type;

    protected float x;
    protected float y;
    protected int width;
    protected int height;

    public Object(Type type, float x, float y, int width, int height) {
        this.type = type;

        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;
    }

    public Object(Type type, float x, float x2, float y, float y2) {
        this.type = type;

        this.x = x;
        this.y = y;

        this.width = (int) Math.abs(x2 - x);
        this.height = (int) Math.abs(y2 - y);
    }

    /**
     * Checks whether this Object collides with other using onCollisionCheck(Object other)
     * then calls onCollisionDetected(Object other, int direction)
     *
     * @param other Object to check collision with
     */
    public final void checkCollision(Object other) {
        while (true) {
            if (!onCollisionDetected(other, onCollisionCheck(other))) {
                return;
            }
        }
    }

    /**
     * Returns one of the Direction constants indicating direction of collision
     *
     * @param other Object to check collision with
     * @return one of the Direction constants indicating direction of collision
     */
    protected int onCollisionCheck(Object other) {
        float x2 = this.getX2(), y2 = this.getY2();
        int direction = Direction.NO;
        if (other.getX() > x && other.getX2() < x2) {
            if (other.getY() > y && other.getY2() < y2) {
                direction = Direction.ALL;
            } else if (other.getY() < y2 && other.getY2() > y2) {
                direction = Direction.TOP;
            } else if (other.getY2() > y && other.getY() < y) {
                direction = Direction.BOT;
            }
        } else if (other.getX2() > x && other.getX() < x) {
            if (other.getY() > y && other.getY2() < y2) {
                direction = Direction.LEFT;
            } else if (other.getY() < y2 && other.getY2() > y2) {
                direction = Direction.TOP_LEFT;
            } else if (other.getY2() > y && other.getY() < y) {
                direction = Direction.BOT_LEFT;
            }
        } else if (other.getX() < x2 && other.getX2() > x) {
            if (other.getY() > y && other.getY2() < y2) {
                direction = Direction.RIGHT;
            } else if (other.getY() < y2 && other.getY2() > y2) {
                direction = Direction.TOP_RIGHT;
            } else if (other.getY2() > y && other.getY() < y) {
                direction = Direction.BOT_RIGHT;
            }
        }
        return direction;
    }

    protected boolean onCollisionDetected(Object other, int direction) {
        return onCollided(other, direction) || other.onCollided(this, direction);
    }

    /**
     * The method is called, when collision is detected
     *
     * @param other     object collision was detected with
     * @param direction direction of the collision
     * @return whether further collision check is required
     */
    protected boolean onCollided(Object other, int direction) {
        return false;
    }

    /**
     * moves Object along the X axis so its left edge will be equal to parameter x
     *
     * @param x desired X position of an Object's left edge
     */
    protected void setX(float x) {
        this.x = x;
    }

    /**
     * moves Object along the Y axis so its left edge will be equal to parameter y
     *
     * @param y desired Y position of an Object's bot edge
     */
    protected void setY(float y) {
        this.y = y;
    }

    /**
     * moves Object along the X axis so its right edge will be equal to parameter x
     *
     * @param x2 desired X position of an Object's right edge
     */
    protected void setX2(float x2) {
        this.x = x2 - width;
    }

    /**
     * moves Object along the Y axis so its right edge will be equal to parameter y
     *
     * @param y2 desired Y position of an Object's top edge
     */
    protected void setY2(float y2) {
        this.y = y2 - height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    protected float getX2() {
        return x + width;
    }

    protected float getY2() {
        return y + height;
    }

    protected int getWidth() {
        return width;
    }

    protected int getHeight() {
        return height;
    }

    protected float getCentreX() {
        return this.x + (this.width / 2);
    }

    protected float getCentreY() {
        return this.y + (this.height / 2);
    }

    protected Type getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "Object: x:" + x + "-" + getX2() + ", y:" + y + "-" + getY2();
    }
}
