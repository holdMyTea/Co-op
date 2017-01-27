package com.forsenboyz.rise42.server.objects.managers;

import com.forsenboyz.rise42.server.objects.characters.Character;
import com.forsenboyz.rise42.server.objects.characters.NPC;

public class EnemyBuilder {

    public static final int SKELETON_MS = 9;
    public static final int SKELETON_HP = 1;

    public static NPC makeSkeleton(float x, float y){
        return new NPC(SKELETON_MS,SKELETON_HP,x,y,0,64,64);
    }
}
