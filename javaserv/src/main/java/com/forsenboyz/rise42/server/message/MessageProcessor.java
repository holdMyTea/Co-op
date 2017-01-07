package com.forsenboyz.rise42.server.message;

import com.forsenboyz.rise42.server.message.IncomeMessage;
import com.forsenboyz.rise42.server.message.Parameters;
import com.forsenboyz.rise42.server.message.OutcomeMessage;
import com.forsenboyz.rise42.server.objects.Hero;
import com.forsenboyz.rise42.server.parser.ConfigParser;
import com.forsenboyz.rise42.server.collisions.CollisionDetector;

public class MessageProcessor {

    private final int MAGE = 0;
    private final int WAR = 1;

    private final int PAUSE_CODE = 1;
    private final int PLAY_CODE = 2;
    private final int MOVE_CODE = 3;
    private final int ROTATE_CODE = 4;
    private final int ANIMATION_CODE = 5;

    private CollisionDetector collisionDetector;

    private Hero mage;
    private Hero war;

    private final OutcomeMessage DEFAULT_PAUSE_OUTCOME_MESSAGE = new OutcomeMessage(-1, PAUSE_CODE);

    public MessageProcessor() {
        this.mage = ConfigParser.getMage();
        this.war = ConfigParser.getWar();

        this.collisionDetector = new CollisionDetector(this.mage, this.war);
    }

    public OutcomeMessage parseMessage(IncomeMessage incomeMessage) {
        if (incomeMessage.SOURCE == MAGE || incomeMessage.SOURCE == WAR) {    // odd man out

            OutcomeMessage outcomeMessage;

            switch (incomeMessage.CODE) {

                case PAUSE_CODE:
                    // drop to PLAY_CODE

                case PLAY_CODE:
                    outcomeMessage = new OutcomeMessage(incomeMessage.SOURCE, incomeMessage.CODE);
                    break;

                case MOVE_CODE:
                    outcomeMessage = move(incomeMessage);
                    break;

                case ROTATE_CODE:
                    outcomeMessage = rotate(incomeMessage);
                    break;

                case ANIMATION_CODE:
                    outcomeMessage = animation(incomeMessage);
                    break;

                default:
                    outcomeMessage = DEFAULT_PAUSE_OUTCOME_MESSAGE; // pause if stuff
            }

            return outcomeMessage;

        }

        return DEFAULT_PAUSE_OUTCOME_MESSAGE;
    }

    private OutcomeMessage move(IncomeMessage incomeMessage) {
        OutcomeMessage outcomeMessage;
        boolean forward = incomeMessage.getParam(Parameters.FOR) == 1;
        int angle = (int) incomeMessage.getParam(Parameters.ANG);

        Hero hero;
        if (incomeMessage.SOURCE == MAGE) {
            hero = mage;
        } else if (incomeMessage.SOURCE == WAR) {
            hero = war;
        } else return DEFAULT_PAUSE_OUTCOME_MESSAGE;

        collisionDetector.moveHero(hero, angle, forward);

        outcomeMessage = new OutcomeMessage(
                incomeMessage.SOURCE,
                MOVE_CODE
        );
        outcomeMessage.addParameter(Parameters.X, hero.getX());
        outcomeMessage.addParameter(Parameters.Y, hero.getY());
        outcomeMessage.addParameter(Parameters.ANG, hero.getAngle());

        return outcomeMessage;
    }

    private OutcomeMessage rotate(IncomeMessage incomeMessage) {
        OutcomeMessage outcomeMessage;
        int angle = (int) incomeMessage.getParam(Parameters.ANG);

        Hero hero;
        if (incomeMessage.SOURCE == MAGE) {
            hero = mage;
        } else if (incomeMessage.SOURCE == WAR) {
            hero = war;
        } else return DEFAULT_PAUSE_OUTCOME_MESSAGE;

        hero.rotate(angle);
        outcomeMessage = new OutcomeMessage(
                incomeMessage.SOURCE,
                ROTATE_CODE
        );
        outcomeMessage.addParameter(Parameters.ANG, hero.getAngle());

        return outcomeMessage;
    }

    private OutcomeMessage animation(IncomeMessage incomeMessage) {
        OutcomeMessage outcomeMessage;
        int angle = (int) incomeMessage.getParam(Parameters.ANG);
        int index = (int) incomeMessage.getParam(Parameters.IND);

        Hero hero;
        if (incomeMessage.SOURCE == MAGE) {
            hero = mage;
        } else if (incomeMessage.SOURCE == WAR) {
            hero = war;
        } else return DEFAULT_PAUSE_OUTCOME_MESSAGE;

        hero.rotate(angle);
        outcomeMessage = new OutcomeMessage(
                incomeMessage.SOURCE,
                ANIMATION_CODE
        );
        outcomeMessage.addParameter(Parameters.ANG, hero.getAngle());
        outcomeMessage.addParameter(Parameters.IND, index);

        return outcomeMessage;
    }

}
