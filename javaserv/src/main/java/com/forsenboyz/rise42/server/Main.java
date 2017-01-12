package com.forsenboyz.rise42.server;

import com.forsenboyz.rise42.server.network.Server;

public class Main {

    Server server;

    public Main(Server server) {
        this.server = server;
    }

    public static void main(String[] args){
        Main main = new Main(new Server(1488));
        main.startServer();
    }

    private void startServer(){
        this.server.listen();
    }
}
