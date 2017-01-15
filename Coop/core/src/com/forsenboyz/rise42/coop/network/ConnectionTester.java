package com.forsenboyz.rise42.coop.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConnectionTester {

    public static int getPortForConnection(String host, int port) {

        Socket socket = new Socket();

        try {
            socket.connect(new InetSocketAddress(host, port));

            byte[] bytes = new byte[4];

            if(socket.getInputStream().read(bytes) < 1){
                return port;
            }

            int result = new Integer(new String(bytes));
            System.out.println("Expidition found port: "+result);

            return result;
        } catch (IOException e) {
            System.out.println("Test connection failed");
            return port;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
