package com.forsenboyz.rise42.coop.network;

import com.forsenboyz.rise42.coop.objects.Character;
import com.forsenboyz.rise42.coop.states.StateManager;

import java.util.Queue;
import static com.forsenboyz.rise42.coop.network.Parameters.*;

class InputHandlingRunnable implements Runnable {

    private Connection connection;
    private StateManager stateManager;

    InputHandlingRunnable(Connection connection, StateManager stateManager) {
        this.connection = connection;
        this.stateManager = stateManager;
    }

    @Override
    public void run() {
        Queue<String> incomes;

        while (this.connection.isConnected()) {
            incomes = this.connection.getIncomeMessages();

            while (incomes != null && !incomes.isEmpty()) {
                Message msg = new Message(incomes.poll());
                System.out.println("!!!!!!!!!!!!!!!!!!" + msg.toString());

                switch (msg.getCode()) {

                    case INIT_CODE:
                        int variant = msg.getParams().get(Parameters.VAR).intValue();
                        this.stateManager.getPlayState().setInitialParameters(variant);
                        break;

                    case PAUSE_CODE:
                        this.stateManager.pause();
                        break;

                    case PLAY_CODE:
                        this.stateManager.play();
                        break;

                    case MOVE_CODE:
                        doMove(msg);
                        break;

                    case ROTATE_CODE:
                        doRotate(msg);
                        break;

                    case ANIMATION_CODE:
                        doAnimation(msg);
                        break;

                }
            }

        }
    }

    private void doMove(Message message){
        determineCharacter(message).move(
                message.getParams().get(Parameters.X),
                message.getParams().get(Parameters.Y),
                message.getParams().get(Parameters.ANG).intValue()
        );
    }

    private void doRotate(Message message){
        System.out.println("there is no "+ANG+" in "+message.toString());
        determineCharacter(message).setRotation(
                message.getParams().get(Parameters.ANG).intValue()
        );
    }

    private void doAnimation(Message message){
        Character character = determineCharacter(message);
        character.setRotation(
                message.getParams().get(Parameters.ANG).intValue()
        );
        character.activateAnimation(
                message.getParams().get(Parameters.IND).intValue()
        );
    }

    private Character determineCharacter(Message message){
        return message.isResponse()
                ? stateManager.getPlayState().getHero()
                : stateManager.getPlayState().getAnotherHero();
    }
}
