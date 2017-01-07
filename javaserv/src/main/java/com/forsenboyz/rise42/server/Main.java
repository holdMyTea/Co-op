package com.forsenboyz.rise42.server;

import com.forsenboyz.rise42.server.network.Server;
import com.forsenboyz.rise42.server.parser.ConfigParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    Server server;

    public Main(Server server) {
        this.server = server;
    }

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Main main = context.getBean("app",Main.class);
        System.out.println("Springed");
        main.startServer();
    }

    private void startServer(){
        this.server.listen();
    }
}
