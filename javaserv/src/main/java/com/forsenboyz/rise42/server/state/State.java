package com.forsenboyz.rise42.server.state;

import com.forsenboyz.rise42.server.message.Message;

public class State {

    private final int MAGE = 0;
    private final int WAR = 1;

    private final int PAUSE_CODE = 1;
    private final int PLAY_CODE = 2;
    private final int MOVE_CODE = 3;
    private final int ROTATE_CODE = 4;

    private final int MAGE_VELOCITY = 8;
    private final int WAR_VELOCITY = 10;

    private final int MAGE_ROTATION = 10;
    private final int WAR_ROTATION = 12;

    private int mageX = 50;
    private int mageY = 100;
    private int mageAngle = 0;

    private int warX = 400;
    private int warY = 100;
    private int warAngle = 270;

    private final Message DEFAULT_PAUSE_MESSAGE = new Message(-1, "r1;", "s1;");

    public Message parseMessage(String raw, int source){
        if(source == MAGE || source == WAR){    // odd man out

            Message message;

            switch(Character.getNumericValue(raw.charAt(1))){

                case PAUSE_CODE:
                    message = new Message(source, "r1;", "s1;");
                    break;

                case PLAY_CODE:
                    message = new Message(source, "r2;", "s2;");
                    break;

                case MOVE_CODE:
                    boolean forward = raw.charAt(raw.indexOf('(')+1) == '1';;

                    if(source == MAGE){
                        moveMage(forward);
                        message = new Message(
                                source,
                                "r3:x("+mageX+"):y("+mageY+");",
                                "s3:x("+mageX+"):y("+mageY+");"
                        );
                    } else if(source == WAR){   //useless because of the top check it is always true
                        moveWar(forward);
                        message = new Message(
                                source,
                                "r3:x("+warX+"):y("+warY+");",
                                "s3:x("+warX+"):y("+warY+");"
                        );
                    } else {
                        message = DEFAULT_PAUSE_MESSAGE;
                    }
                    break;

                case ROTATE_CODE:
                    boolean clockwise = raw.charAt(raw.indexOf('(')+1) == '1';

                    if(source == MAGE){
                        rotateMage(clockwise);
                        message = new Message(
                                source,
                                "r4:ang("+mageAngle+");",
                                "s4:ang("+mageAngle+");"
                        );
                    } else if(source == WAR){
                        rotateWar(clockwise);
                        message = new Message(
                                source,
                                "r4:ang("+warAngle+");",
                                "s4:ang("+warAngle+");"
                        );
                    } else{
                        message = DEFAULT_PAUSE_MESSAGE;
                    }
                    break;

                default:
                    message = DEFAULT_PAUSE_MESSAGE; // pause if stuff
            }

            return message;

        }

        return DEFAULT_PAUSE_MESSAGE;
    }

    private void rotateMage(boolean clockwise){
        mageAngle = handleAngle(mageAngle, clockwise ? MAGE_ROTATION * (-1) : MAGE_ROTATION);
    }

    private void rotateWar(boolean clockwise){
        warAngle = handleAngle(warAngle, clockwise ? WAR_ROTATION * (-1) : WAR_ROTATION);
    }

    private int handleAngle(int initial, int delta){
        int check = initial + delta;

        if(check < 0){
            return 360 + check;
        } else if(check >= 360){
            return check - 360;
        } else return check;
    }

    //TODO: IntelliJ (SkyNet) says following two methods are copypasta
    private void moveMage(boolean forward){
        if(forward){
            mageX += MAGE_VELOCITY * Math.cos(Math.toRadians(mageAngle));
            mageY += MAGE_VELOCITY * Math.sin(Math.toRadians(mageAngle));
        } else{
            int backDegree = handleAngle(mageAngle, 180);
            mageX += MAGE_VELOCITY * Math.cos(Math.toRadians(backDegree));
            mageY += MAGE_VELOCITY * Math.sin(Math.toRadians(backDegree));
        }
    }

    private void moveWar(boolean forward){
        if(forward){
            warX += WAR_VELOCITY * Math.cos(Math.toRadians(warAngle));
            warY += WAR_VELOCITY * Math.sin(Math.toRadians(warAngle));
        } else{
            int backDegree = handleAngle(warAngle, 180);
            warX += WAR_VELOCITY * Math.cos(Math.toRadians(backDegree));
            warY += WAR_VELOCITY * Math.sin(Math.toRadians(backDegree));
        }
    }
}
