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
    private int warAngle = 0;

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
                    char direction = raw.charAt(raw.indexOf('(')+1);

                    if(source == MAGE){
                        if(direction == '1'){
                            moveMageRight();
                        } else if(direction == '0'){
                            moveMageLeft();
                        }
                        message = new Message(
                                source,
                                "r3:pos("+mageX+");",
                                "s3:pos("+mageX+");"
                        );
                    } else if(source == WAR){   //useless because of the top check it is always true
                        if(direction == '1'){
                            moveWarRight();
                        } else if(direction == '0'){
                            moveWarLeft();
                        }
                        message = new Message(
                                source,
                                "r3:pos("+warX+");",
                                "s3:pos("+warX+");"
                        );
                    } else {
                        message = DEFAULT_PAUSE_MESSAGE;
                    }
                    break;

                case 4:
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

    private void moveMageLeft(){
        this.mageX -= MAGE_VELOCITY;
    }

    private void moveMageRight(){
        this.mageX += MAGE_VELOCITY;
    }

    private void moveWarLeft(){
        this.warX -= WAR_VELOCITY;
    }

    private void moveWarRight(){
        this.warX += WAR_VELOCITY;
    }
}
