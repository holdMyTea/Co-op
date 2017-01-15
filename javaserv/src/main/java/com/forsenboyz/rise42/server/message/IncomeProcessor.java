package com.forsenboyz.rise42.server.message;

import com.forsenboyz.rise42.server.cycle.MainCycle;

public class IncomeProcessor {

    private static final int MAGE = 0;
    private static final int WAR = 1;

    private static final int PAUSE_CODE = 1;
    private static final int PLAY_CODE = 2;
    private static final int MOVE_CODE = 3;
    private static final int ROTATE_CODE = 4;
    private static final int ACTION_CODE = 5;

    private static final String ANG = "ANG";
    private static final String FOR = "FOR";
    private static final String IND = "IND";

    private MainCycle mainCycle;

    public IncomeProcessor(MainCycle mainCycle) {
        this.mainCycle = mainCycle;
    }

    public void parseMessage(String msg, int source) {
        IncomeMessage incomeMessage = new IncomeMessage(msg,source);
        if (incomeMessage.SOURCE == MAGE || incomeMessage.SOURCE == WAR) {    // odd man out

            switch (incomeMessage.CODE) {

                case PAUSE_CODE:
                    mainCycle.pause();
                    break;

                case PLAY_CODE:
                    mainCycle.play();
                    break;

                case MOVE_CODE:
                    move(incomeMessage);
                    break;

                case ROTATE_CODE:
                    rotate(incomeMessage);
                    break;

                case ACTION_CODE:
                    action(incomeMessage);
                    break;

                default:
                    mainCycle.pause();
            }

        }
    }

    private void move(IncomeMessage incomeMessage) {
        boolean forward = incomeMessage.getParam(FOR) == 1;
        int angle = (int) incomeMessage.getParam(ANG);

        if (incomeMessage.SOURCE == MAGE) {
            mainCycle.moveMage(angle,forward);
        } else if (incomeMessage.SOURCE == WAR) {
            mainCycle.moveWar(angle,forward);
        }
    }

    private void rotate(IncomeMessage incomeMessage) {
        int angle = (int) incomeMessage.getParam(ANG);

        if (incomeMessage.SOURCE == MAGE) {
            mainCycle.rotateMage(angle);
        } else if (incomeMessage.SOURCE == WAR) {
            mainCycle.rotateWar(angle);
        }
    }

    private void action(IncomeMessage incomeMessage) {
        int index = (int) incomeMessage.getParam(IND);
        int angle = (int) incomeMessage.getParam(ANG);

        if (incomeMessage.SOURCE == MAGE) {
            System.out.println("INCOME ACTION MAGE!!!!!!!!!!!!!!!!!!");
            mainCycle.actionMage(index, angle);
        } else if (incomeMessage.SOURCE == WAR) {
            mainCycle.actionWar(index, angle);
        }
    }

}