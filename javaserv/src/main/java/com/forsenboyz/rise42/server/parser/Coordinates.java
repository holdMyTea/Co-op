package com.forsenboyz.rise42.server.parser;

public class Coordinates {

    public float x;
    public float y;

    public Coordinates(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        Class coordinates = Coordinates.class;

        if(! obj.getClass().equals(coordinates)) return false;

        try {
            return (coordinates.getField("x").getFloat(obj) == this.x)
                    && (coordinates.getField("y").getFloat(obj) == this.y);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Coordinates: x="+x+"; y="+y+";";
    }
}
