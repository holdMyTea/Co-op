package com.forsenboyz.rise42.coop.network;

import com.forsenboyz.rise42.coop.log.Log;
import com.forsenboyz.rise42.coop.log.Time;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
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

    public Connection(String host, int port) {
        this.HOST = host;
        this.PORT = port;

        log = Log.getInstance();
        time = new Time("SSS");

        incomeMessages = new ArrayDeque<String>();
        outcomeMessages = new ArrayDeque<String>();
    }

    public void connect() {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(HOST, PORT));
            log.network("Connected");

            inputStream = socket.getInputStream();
            outputWriter = new OutputStreamWriter(socket.getOutputStream());
            log.network("Streams opened");

            startInputThread();
            startOutputThread();
            log.network("Threads started");
        } catch (IOException ioEx) {
            log.network("Connection init " + ioEx.toString());
        }
    }

    public void sendMessage(String message) {
        synchronized (outcomeMessages) {
            outcomeMessages.add("c"+message+"#"+time.getTime()+";");
            outcomeMessages.notify();
            log.network("Sending in q: " + message);
        }
    }

    public void sendMessage(int code) {
        sendMessage(Integer.toString(code));
    }

    synchronized Queue<String> getIncomeMessages() {
        return incomeMessages;
    }

    boolean isConnected() {
        return this.socket.isConnected();
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
                                }
                                //incomeMessages.wait(200);
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
