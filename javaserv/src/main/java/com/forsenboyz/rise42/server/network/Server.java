package com.forsenboyz.rise42.server.network;

import com.forsenboyz.rise42.server.message.Message;
import com.forsenboyz.rise42.server.state.State;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Server {

    private final String HOST;
    private final int PORT;
    private final State STATE;

    private ServerSocket serverSocket;

    ArrayList<Connection> connections;

    SimpleDateFormat dateFormat;

    Server(String host, int port, State state) {
        this.HOST = host;
        this.PORT = port;
        this.STATE = state;

        dateFormat = new SimpleDateFormat("mm:ss.SSS");

        connections = new ArrayList<Connection>(2);
    }

    public void listen() {
        try {
            serverSocket = new ServerSocket(PORT);

            System.out.println("Listening");
            while(connections.size() < 2) {
                connections.add(new Connection(connections.size(), serverSocket.accept(), this));
                System.out.println("Got one");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void spreadMessage(String raw, int source){
        Message message = this.STATE.parseMessage(raw,source);
        System.out.println(dateFormat.format(new Date())+": "+message.toString());
        for(Connection connection : connections){
            connection.sendMessage(message);
        }
    }
}
