package com.forsenboyz.rise42.server.state;

import com.forsenboyz.rise42.server.message.Message;

public class State {

    private final int MAGE = 0;
    private final int WAR = 1;

    private final int PAUSE_CODE = 1;
    private final int PLAY_CODE = 2;
    private final int MOVE_CODE = 3;

    private final int MAGE_VELOCITY = 8;
    private final int WAR_VELOCITY = 10;

    private int mageX;
    private int mageY;

    private int warX;
    private int warY;

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
                    char direction = raw.charAt(5);

                    if(source == MAGE){
                        if(direction == '+'){
                            moveMageRight();
                        } else if(direction == '-'){
                            moveMageLeft();
                        }
                        message = new Message(
                                source,
                                "r3:pos("+mageX+");",
                                "s3:pos("+mageX+");"
                        );
                    } else if(source == WAR){   //useless because of the top check it is always true
                        if(direction == '+'){
                            moveWarRight();
                        } else if(direction == '-'){
                            moveWarLeft();
                        }
                        message = new Message(
                                source,
                                "r3:pos("+warX+");",
                                "s3:pos("+warX+");"
                        );
                    } else {
                        message = new Message(source, "r1;", "s1;");
                    }
                    break;

                default:
                    message = new Message(source, "r1;", "s1;"); // pause if stuff
            }

            return message;

        }

        return new Message(source, "r1;", "s1;");
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
