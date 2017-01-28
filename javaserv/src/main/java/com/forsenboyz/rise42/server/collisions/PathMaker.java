package com.forsenboyz.rise42.server.collisions;

import com.forsenboyz.rise42.server.objects.Object;
import com.forsenboyz.rise42.server.objects.characters.NPC;
import com.forsenboyz.rise42.server.parser.Coordinates;

import java.util.ArrayList;
import java.util.Collections;

public class PathMaker {

    public static void makePath(NPC npc, ArrayList<Object> wallBlocks) {

        Coordinates leftPoint, rightPoint;

        if(npc.getCentreX() < npc.getTarget().getCentreX()){
            leftPoint = npc.getCoordinates();
            rightPoint = npc.getTarget().getCoordinates();
        } else{
            leftPoint = npc.getTarget().getCoordinates();
            rightPoint = npc.getCoordinates();
        }

        float k = (rightPoint.y - leftPoint.y)
                / (rightPoint.x - leftPoint.x);

        // if target is on the left, collided blocks are reversed for proper sequence
        if (rightPoint.equals(npc.getCoordinates())){
            Collections.reverse(wallBlocks);

            for (Object block : wallBlocks){
                float h = rightPoint.y - (k * (rightPoint.x - block.getX2()));
            }

        } else {


        }


    }
}
