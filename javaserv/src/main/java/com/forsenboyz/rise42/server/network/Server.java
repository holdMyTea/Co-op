package com.forsenboyz.rise42.server.network;

import com.forsenboyz.rise42.server.cycle.MainCycle;
import com.forsenboyz.rise42.server.message.IncomeProcessor;
import com.forsenboyz.rise42.server.message.OutcomeProcessor;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {

    private int salutePort;
    private int player1Port;
    private int player2Port;
    private int spectatorPort;

    private MainCycle mainCycle;
    private IncomeProcessor incomeProcessor;

    // hope it's the case
    private CopyOnWriteArrayList<Connection> connections;

    private SimpleDateFormat dateFormat;

    public Server(int salutePort, int player1Port, int player2Port, int spectatorPort) {
        this.salutePort = salutePort;
        this.player1Port = player1Port;
        this.player2Port = player2Port;
        this.spectatorPort = spectatorPort;

        connections = new CopyOnWriteArrayList<>();

        this.mainCycle = new MainCycle(this);
        this.incomeProcessor = mainCycle.getIncomeProcessor();

        dateFormat = new SimpleDateFormat("mm:ss.SSS");
    }

    public void listen() {
        makeSaluteThread().start();
    }

    void processMessage(String raw, int source) {
        for (String msg : raw.split(";")) {
            this.incomeProcessor.parseMessage(msg, source);
        }
    }

    public void spreadMessage(String message) {
        for (Connection connection : connections) {
            if (message != null) {
                System.out.println("sending: " + message);
                connection.sendMessage(message);
            }
        }
    }

    private Thread makeSaluteThread() {
        Thread thread = new Thread(
                () -> {
                    try {
                        // here?
                        mainCycle.runCycle();

                        ServerSocket saluteSocket;
                        OutputStreamWriter outputStream;

                        System.out.println("Listening");
                        while (true) {
                            System.out.println("Collect them all: " + Thread.activeCount());
                            saluteSocket = new ServerSocket(salutePort);
                            outputStream = new OutputStreamWriter(
                                    saluteSocket.accept().getOutputStream()
                            );
                            if (connections.size() == 0) {
                                outputStream.write(Integer.toString(player1Port));
                                makePlayer1Thread().start();
                            } else if (connections.size() == 1) {
                                outputStream.write(Integer.toString(player2Port));
                                makePlayer2Thread().start();
                                //break; //TODO: deal with it
                            } else {
                                outputStream.write(Integer.toString(spectatorPort));
                                break;
                            }
                            outputStream.flush();
                            outputStream.close();
                            saluteSocket.close();
                        }
                        System.out.println("Salute is dead");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.setDaemon(false);
        return thread;
    }

    private Thread makePlayer1Thread() {
        Thread thread = new Thread(
                () -> {
                    try {
                        ServerSocket player1Socket = new ServerSocket(player1Port);

                        connections.add(
                                new Connection(0, player1Socket.accept(), this)
                        );
                        System.out.println("Got the p1");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.setDaemon(true);
        return thread;
    }

    private Thread makePlayer2Thread() {
        Thread thread = new Thread(
                () -> {
                    try {
                        ServerSocket player2Socket = new ServerSocket(player2Port);

                        System.out.println("Waiting for p2");
                        connections.add(
                                new Connection(1, player2Socket.accept(), this)
                        );
                        System.out.println("Got the p2");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        thread.setDaemon(true);
        return thread;
    }
}
