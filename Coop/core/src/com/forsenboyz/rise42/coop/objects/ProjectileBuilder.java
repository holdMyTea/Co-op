package com.forsenboyz.rise42.coop.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ProjectileBuilder {

    public static RotatableObject makeProjectile(
            float x,
            float y,
            int angle,
            int type
    ){
        switch(type){
            case 0:
                return new RotatableObject("firbal.png", x, y, angle);
            case 1:
                return new RotatableObject("firbalexpl.png",x,y);
            default:
                return new RotatableObject(
                        new TextureRegion(new Texture("block.png"), 32, 32),
                        x,y,angle
                );

        }
    }
}
