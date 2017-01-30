package com.forsenboyz.rise42.server.collisions;

import com.forsenboyz.rise42.server.objects.Object;
import com.forsenboyz.rise42.server.objects.characters.NPC;
import com.forsenboyz.rise42.server.parser.Coordinates;

import java.util.ArrayList;
import java.util.Collections;

public class PathMaker {

    public static void makePath(NPC npc, ArrayList<Object> wallBlocks) {

        /*if(wallBlocks.isEmpty()){
            System.out.println("Always empty");

        }*/

        Coordinates leftPoint, rightPoint;

        if(npc.getCentreX() < npc.getTarget().getCentreX()){
            leftPoint = npc.getCoordinates();
            rightPoint = npc.getTarget().getCoordinates();
        } else{
            leftPoint = npc.getTarget().getCoordinates();
            rightPoint = npc.getCoordinates();
        }

        System.out.println("Left " + leftPoint);
        System.out.println("Right " + rightPoint);

        System.out.println(leftPoint.y - rightPoint.y);
        System.out.println(leftPoint.x - rightPoint.x);
        System.out.println((rightPoint.y - leftPoint.y)
                / (rightPoint.x - leftPoint.x));

        // if target is on the left, collided blocks are reversed for proper sequence
        if (rightPoint.equals(npc.getCoordinates())){
            Collections.reverse(wallBlocks);

            float k = (rightPoint.y - leftPoint.y)
                    / (rightPoint.x - leftPoint.x);
            System.out.println(k);

            for (Object block : wallBlocks){
                float h = rightPoint.y - (k * (rightPoint.x - block.getX2()));

                System.out.println("Block bot: "+block.getY()+" h: "+h+" block top: "+block.getY2());

                if(block.getY() < h && h < block.getCentreY()){
                    npc.addToPath(
                            new Coordinates(
                                    (int) block.getX2() + (npc.getWidth()*3) + CollisionDetector.SOME_GAP_CONST,
                                    (int) block.getY() - (npc.getWidth()*2) + CollisionDetector.SOME_GAP_CONST
                            )
                    );
                    break;
                } else if(block.getCentreY() <= h && h < block.getY2()){
                    npc.addToPath(
                            new Coordinates(
                                    (int) block.getX2() + (npc.getWidth()*3) + CollisionDetector.SOME_GAP_CONST,
                                    (int) block.getY2() + (npc.getWidth()*2) + CollisionDetector.SOME_GAP_CONST
                            )
                    );
                    break;
                }
            }

        } else {

            float k = (leftPoint.y - rightPoint.y)
                    / (leftPoint.x - rightPoint.x);
            System.out.println(k);

            for (Object block : wallBlocks){
                float h = leftPoint.y - (k * (leftPoint.x - block.getX()));

                System.out.println("Block bot: "+block.getY()+" h: "+h+" block top: "+block.getY2());

                if(block.getY() < h && h < block.getCentreY()){
                    npc.addToPath(
                            new Coordinates(
                                    (int) block.getX() + npc.getWidth() + CollisionDetector.SOME_GAP_CONST,
                                    (int) block.getY() + npc.getWidth() + CollisionDetector.SOME_GAP_CONST
                            )
                    );
                    break;
                } else if(block.getCentreY() <= h && h < block.getY2()){
                    npc.addToPath(
                            new Coordinates(
                                    (int) block.getX() + npc.getWidth() + CollisionDetector.SOME_GAP_CONST,
                                    (int) block.getY2() + npc.getWidth() + CollisionDetector.SOME_GAP_CONST
                            )
                    );
                    break;
                }
            }
        }

        npc.addToPath(
                npc.getTarget().getCoordinates()
        );

    }
}
