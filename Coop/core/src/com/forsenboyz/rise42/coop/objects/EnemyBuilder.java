package com.forsenboyz.rise42.coop.objects;

import com.badlogic.gdx.graphics.Texture;

public class EnemyBuilder {

    public static RotatableObject buildSkeleton(float x, float y, int angle){
        return new RotatableObject(
                new Texture("skull.png"),
                x,
                y,
                angle
        );
    }
}
