package com.forsenboyz.rise42.server.message;

import com.forsenboyz.rise42.server.cycle.MainCycle;
import static com.forsenboyz.rise42.server.message.Codes.*;

public class IncomeProcessor {

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
        boolean forward = incomeMessage.getParam(Parameters.FOR) == 1;
        int angle = (int) incomeMessage.getParam(Parameters.ANG);

        if (incomeMessage.SOURCE == MAGE) {
            mainCycle.moveMage(angle,forward);
        } else if (incomeMessage.SOURCE == WAR) {
            mainCycle.moveWar(angle,forward);
        }
    }

    private void rotate(IncomeMessage incomeMessage) {
        int angle = (int) incomeMessage.getParam(Parameters.ANG);

        if (incomeMessage.SOURCE == MAGE) {
            mainCycle.rotateMage(angle);
        } else if (incomeMessage.SOURCE == WAR) {
            mainCycle.rotateWar(angle);
        }
    }

    private void action(IncomeMessage incomeMessage) {
        int index = (int) incomeMessage.getParam(Parameters.IND);
        int angle = (int) incomeMessage.getParam(Parameters.ANG);

        if (incomeMessage.SOURCE == MAGE) {
            mainCycle.actionMage(index, angle);
        } else if (incomeMessage.SOURCE == WAR) {
            mainCycle.actionMage(index, angle);
        }
    }

}
