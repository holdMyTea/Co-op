package com.forsenboyz.rise42.coop.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ProjectileBuilder {

    public static RotatableObject makeProjectile(
            float x,
            float y,
            int angle,
            int type,
            boolean destroyed
    ){
        switch(type){
            case 0:
                return new RotatableObject(destroyed ? "firbalexpl.png" : "firbal.png", x, y, angle);
            default:
                return new RotatableObject(
                        new TextureRegion(new Texture("block.png"), 32, 32),
                        x,y,angle
                );

        }
    }
}
