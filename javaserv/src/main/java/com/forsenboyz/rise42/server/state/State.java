package com.forsenboyz.rise42.server.state;

import com.forsenboyz.rise42.server.message.IncomeMessage;
import com.forsenboyz.rise42.server.message.Parameters;
import com.forsenboyz.rise42.server.message.OutcomeMessage;

public class State {

    private final int MAGE = 0;
    private final int WAR = 1;

    private final int PAUSE_CODE = 1;
    private final int PLAY_CODE = 2;
    private final int MOVE_CODE = 3;
    private final int ROTATE_CODE = 4;

    private final int MAGE_VELOCITY = 6;
    private final int WAR_VELOCITY = 8;

    private float mageX = 50;
    private float mageY = 100;
    private int mageAngle = 0;

    private float warX = 400;
    private float warY = 100;
    private int warAngle = 270;

    private final OutcomeMessage DEFAULT_PAUSE_OUTCOME_MESSAGE =
            new OutcomeMessage(-1, "r1;", "s1;");

    public OutcomeMessage parseMessage(IncomeMessage incomeMessage) {
        if (incomeMessage.SOURCE == MAGE || incomeMessage.SOURCE == WAR) {    // odd man out

            OutcomeMessage outcomeMessage;

            switch (incomeMessage.CODE) {

                case PAUSE_CODE:
                    outcomeMessage = new OutcomeMessage(incomeMessage.SOURCE, "r1;", "s1;");
                    break;

                case PLAY_CODE:
                    outcomeMessage = new OutcomeMessage(incomeMessage.SOURCE, "r2;", "s2;");
                    break;

                case MOVE_CODE:
                    outcomeMessage = move(incomeMessage);
                    break;

                case ROTATE_CODE:
                    outcomeMessage = rotate(incomeMessage);
                    break;

                default:
                    outcomeMessage = DEFAULT_PAUSE_OUTCOME_MESSAGE; // pause if stuff
            }

            return outcomeMessage;

        }

        return DEFAULT_PAUSE_OUTCOME_MESSAGE;
    }

    private OutcomeMessage move(IncomeMessage incomeMessage) {
        rotate(incomeMessage);
        OutcomeMessage outcomeMessage;
        boolean forward = incomeMessage.getParam(Parameters.FOR) == 1;

        if (incomeMessage.SOURCE == MAGE) {
            mageX = checkBorders(computeMoveX(forward, mageX, MAGE_VELOCITY, mageAngle));
            mageY = checkBorders(computeMoveY(forward, mageY, MAGE_VELOCITY, mageAngle));
            outcomeMessage = new OutcomeMessage(
                    incomeMessage.SOURCE,
                    "r3:"+Parameters.X+"(" + mageX + "):"+Parameters.Y+"(" + mageY + "):"
                            +Parameters.ANG+"(" + mageAngle + ");",
                    "s3:"+Parameters.X+"(" + mageX + "):"+Parameters.Y+"(" + mageY + "):"
                            +Parameters.ANG+"(" + mageAngle + ");"
            );
        } else if (incomeMessage.SOURCE == WAR) {   //useless because of the top check it is always true
            warX = checkBorders(computeMoveX(forward, warX, WAR_VELOCITY, warAngle));
            warY = checkBorders(computeMoveY(forward, warY, WAR_VELOCITY, warAngle));
            outcomeMessage = new OutcomeMessage(
                    incomeMessage.SOURCE,
                    "r3:"+Parameters.X+"(" + warX + "):"+Parameters.Y+"(" + warY + "):"
                            +Parameters.ANG+"(" + warAngle + ");",
                    "s3:"+Parameters.X+"(" + warX + "):"+Parameters.Y+"(" + warY + "):"
                            +Parameters.ANG+"(" + warAngle + ");"
            );
        } else {
            outcomeMessage = DEFAULT_PAUSE_OUTCOME_MESSAGE;
        }
        return outcomeMessage;
    }

    private OutcomeMessage rotate(IncomeMessage incomeMessage) {
        OutcomeMessage outcomeMessage;
        int angle = (int) incomeMessage.getParam(Parameters.ANG);

        if (incomeMessage.SOURCE == MAGE) {
            mageAngle = angle;
            outcomeMessage = new OutcomeMessage(
                    incomeMessage.SOURCE,
                    "r4:" + Parameters.ANG + "(" + mageAngle + ");",
                    "s4:" + Parameters.ANG + "(" + mageAngle + ");"
            );
        } else if (incomeMessage.SOURCE == WAR) {
            warAngle = angle;
            outcomeMessage = new OutcomeMessage(
                    incomeMessage.SOURCE,
                    "r4:" + Parameters.ANG + "(" + warAngle + ");",
                    "s4:" + Parameters.ANG + "(" + warAngle + ");"
            );
        } else {
            outcomeMessage = DEFAULT_PAUSE_OUTCOME_MESSAGE;
        }

        return outcomeMessage;
    }

    private int convertAngle(int initial, int delta) {
        int check = initial + delta;

        if (check < 0) {
            return 360 + check;
        } else if (check >= 360) {
            return check - 360;
        } else return check;
    }

    private int computeMoveX(boolean forward, float initialX, int velocity, int angle){
        return forward ?
                (int) (initialX + velocity * Math.cos(Math.toRadians(angle))) :
                (int) (initialX + velocity * Math.cos(Math.toRadians(convertAngle(angle, 180))));
    }

    private int computeMoveY(boolean forward, float initialY, int velocity, int angle){
        return forward ?
                (int) (initialY + velocity * Math.sin(Math.toRadians(angle))) :
                (int) (initialY + velocity * (int) Math.sin(Math.toRadians(convertAngle(angle, 180))));
    }

    private int checkBorders(int position){
        if(position < Sizes.HERO_MIN_Y){
            return Sizes.HERO_MIN_Y;
        } else if(position > Sizes.HERO_MAX_Y){
            return Sizes.HERO_MAX_Y;
        } else return position;
    }

}
