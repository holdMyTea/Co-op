package com.forsenboyz.rise42.server.network;

import com.forsenboyz.rise42.server.cycle.MainCycle;
import com.forsenboyz.rise42.server.message.IncomeProcessor;
import com.forsenboyz.rise42.server.message.OutcomeProcessor;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Server {

    private final int PORT;
    private ServerSocket serverSocket;

    private MainCycle mainCycle;
    private IncomeProcessor incomeProcessor;
    private OutcomeProcessor outcomeProcessor;

    private ArrayList<Connection> connections;

    private SimpleDateFormat dateFormat;

    public Server(int port) {
        this.PORT = port;

        this.mainCycle = new MainCycle();
        this.incomeProcessor = mainCycle.getIncomeProcessor();
        this.outcomeProcessor = mainCycle.getOutcomeProcessor();

        dateFormat = new SimpleDateFormat("mm:ss.SSS");

        connections = new ArrayList<Connection>(2);
    }

    public void listen() {
        try {
            serverSocket = new ServerSocket(PORT);

            System.out.println("Listening");
            while (connections.size() < 2) {
                connections.add(new Connection(connections.size(), serverSocket.accept(), this));
                System.out.println("Got one");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void processMessage(String raw, int source) {
        for (String msg : raw.split(";")) {
            this.incomeProcessor.parseMessage(msg, source);
        }
    }

    private void startSpreadingThread() {
        Thread spreadThread = new Thread(
                () -> {
                    while (isEverythingConnected()) {
                        for (Connection connection : connections) {
                            connection.sendMessage(outcomeProcessor.getMessage());
                        }
                    }
                }
        );
        spreadThread.setDaemon(true);
        spreadThread.start();
    }

    private boolean isEverythingConnected() {
        boolean result = true;
        for (Connection connection : connections) {
            result = result && connection.isConnected();
        }
        return result;
    }
}
