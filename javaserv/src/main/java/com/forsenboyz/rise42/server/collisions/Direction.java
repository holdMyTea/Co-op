package com.forsenboyz.rise42.server.collisions;

public class Direction {

    public static final int TOP_LEFT = 1;
    public static final int TOP = 2;
    public static final int TOP_RIGHT = 3;

    public static final int LEFT = 4;
    public static final int NO = 5;
    public static final int RIGHT = 6;

    public static final int BOT_LEFT = 7;
    public static final int BOT = 8;
    public static final int BOT_RIGHT = 9;

    public static final int ALL = 10;

    public static int oppositeDirection(int direction){
        if(direction == ALL) return ALL;

        return 10 - direction;
    }
}
