package com.forsenboyz.rise42.server.parser;

public class Coordinates {

    public int x;
    public int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        Class coordinates = Coordinates.class;

        if(! obj.getClass().equals(coordinates)) return false;

        try {
            return (coordinates.getField("x").getInt(obj) == this.x)
                    && (coordinates.getField("y").getInt(obj) == this.y);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }
}
