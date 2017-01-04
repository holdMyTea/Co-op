package com.forsenboyz.rise42.coop.network;

import com.badlogic.gdx.utils.TimeUtils;
import com.forsenboyz.rise42.coop.log.Log;
import com.forsenboyz.rise42.coop.log.Time;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Connection {

    private final String HOST;
    private final int PORT;

    private Socket socket;
    private Log log;

    private OutputStreamWriter outputWriter;
    private InputStream inputStream;

    private final Queue<String> incomeMessages;
    private final Queue<String> outcomeMessages;

    private final Time time;

    private long lastOutputTime;

    Connection(String host, int port) {
        this.HOST = host;
        this.PORT = port;

        log = Log.getInstance();
        time = new Time("mm:ss.SSS");

        incomeMessages = new ArrayDeque<String>();
        outcomeMessages = new ArrayDeque<String>();
    }

    void connect() {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(HOST, PORT));
            log.network("Connected");
        } catch (IOException ioex){
            log.network("No connection");
            return;
        }

        try {
            inputStream = socket.getInputStream();
            outputWriter = new OutputStreamWriter(socket.getOutputStream());
            log.network("Streams opened");

            lastOutputTime = TimeUtils.millis();

            startInputThread();
            startOutputThread();
            log.network("Threads started");
        } catch (IOException ioEx) {
            //log.network("Connection init " + ioEx.toString());
            ioEx.printStackTrace();
        }
    }

    void sendMessage(String message) {
        synchronized (outcomeMessages) {
            lastOutputTime = TimeUtils.millis();
            outcomeMessages.add("c"+message+"#"+time.getTime()+";");
            outcomeMessages.notify();
            log.network("Sending in q: " + message);
        }
    }

    synchronized Queue<String> getIncomeMessages() {
        return incomeMessages;
    }

    long getLastOutputTime() {
        return lastOutputTime;
    }

    //TODO: apparently, does not what is expected
    boolean isConnected() {
        return (this.socket != null) && this.socket.isConnected() ;
    }

    private void startInputThread(){
        new Thread(
                () -> {
                    try {
                        while (this.socket.isConnected()) {
                            String s = readMessage();
                            log.network("Input read: "+s);

                            synchronized (incomeMessages) {
                                if (s != null) {
                                    incomeMessages.add(s);
                                    log.network("Added: "+s);
                                    log.network("Current input q: "+incomeMessages.size());
                                } else System.exit(0);
                            }
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
        ).start();
    }

    private void startOutputThread(){
        new Thread(
                () -> {
                    try {
                        while (this.socket.isConnected()) {
                            synchronized (outcomeMessages) {
                                while (!outcomeMessages.isEmpty()) {
                                    log.network("Sending: "+outcomeMessages.peek());
                                    outputWriter.write(outcomeMessages.poll());
                                    outputWriter.flush();
                                }
                                outcomeMessages.wait();
                            }
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
        ).start();
    }

    private String readMessage() {
        try {
            byte[] buffer = new byte[255];
            if (inputStream.read(buffer) > 0) {
                ArrayList<Character> list = new ArrayList<>();
                for (byte b : buffer) {
                    //System.out.println(b);
                    if (b == 0) {
                        break;
                    }
                    list.add((char) b);
                }
                return list.toString().replaceAll("[,\\s\\[\\]]", "");
            }
            return null;
        } catch (IOException e) {
            log.network("Error while reading: " + e.toString());
            return null;
        }
    }

}
