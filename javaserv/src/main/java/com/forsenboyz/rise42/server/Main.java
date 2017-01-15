package com.forsenboyz.rise42.server;

import com.forsenboyz.rise42.server.cycle.MainCycle;
import com.forsenboyz.rise42.server.network.Server;

public class Main {

    private Server server;

    private Main() {
        server = new Server(
                1488,
                1489,
                1490,
                1491
        );
    }

    public static void main(String[] args){
        Main main = new Main();
        main.startServer();

        /*MainCycle mainCycle = new MainCycle();
        mainCycle.runCycle();
        mainCycle.play();*/
    }

    private void startServer(){
        server.listen();
    }

}
