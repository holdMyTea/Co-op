package com.forsenboyz.rise42.coop.network;

import com.forsenboyz.rise42.coop.objects.Character;
import com.forsenboyz.rise42.coop.states.StateManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
                //TODO: init msg

                JsonObject msg = new JsonParser().parse(incomes.poll()).getAsJsonObject();

                stateManager.getPlayState();

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
