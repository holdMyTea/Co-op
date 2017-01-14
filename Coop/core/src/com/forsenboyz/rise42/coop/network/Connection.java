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
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Connection {

    private final String HOST;
    private final int PORT;

    private Socket socket;
    private Log log;

    private InputMessageHandler inputMessageHandler;

    private OutputStreamWriter outputWriter;
    private InputStream inputStream;

    //TODO: concurrent queues
    private final Queue<String> outcomeMessages;
    private final Queue<String> incomeMessages;

    private final Time time;

    private long lastOutputTime;

    // stores parts of read buffer, that were read with current message, but belong to the next one
    private String messagePart = "";

    Connection(String host, int port, InputMessageHandler inputMessageHandler) {
        this.HOST = host;
        this.PORT = port;

        this.inputMessageHandler = inputMessageHandler;

        log = Log.getInstance();
        time = new Time("SSS");

        outcomeMessages = new ArrayDeque<String>();
        incomeMessages = new ConcurrentLinkedQueue<String>();
    }

    void connect() {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(HOST, PORT));
            log.network("Connected");
        } catch (IOException ioex) {
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
            outcomeMessages.add("c" + message + "#" + time.getTime() + ";");
            outcomeMessages.notify();
            //log.network("Sending in q: " + message);
        }
    }

    long getLastOutputTime() {
        return lastOutputTime;
    }

    //TODO: apparently, does not what is expected
    boolean isConnected() {
        return (this.socket != null) && this.socket.isConnected();
    }

    private void startInputThread() {
        new Thread(
                () -> {
                    try {
                        while (this.socket.isConnected()) {
                            String s = readMessage();
                            log.network("Input read: " + s);

                                if (s != null) {
                                    incomeMessages.add(s);
                                    inputMessageHandler.parse(incomeMessages);
                                    log.network("Current input q: " + incomeMessages.size());
                                } else System.exit(0);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }

    private void startOutputThread() {
        new Thread(
                () -> {
                    try {
                        while (this.socket.isConnected()) {
                            synchronized (outcomeMessages) {
                                while (!outcomeMessages.isEmpty()) {
                                    log.network("Sending: " + outcomeMessages.peek());
                                    outputWriter.write(outcomeMessages.poll());
                                    outputWriter.flush();
                                }
                                outcomeMessages.wait();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        ).start();
    }

    private String readMessage() {
        try {
            byte[] buffer = new byte[350];
            if (inputStream.read(buffer) > 0) {

                String[] read =
                        (
                                messagePart +
                                        new String(buffer).replaceAll(
                                                Character.toString((char) 0),
                                                ""
                                        )
                        ).split(";");

                if (read.length > 1) {
                    messagePart = read[1];
                } else messagePart = "";
                return read[0] ;
            }
            return null;
        } catch (IOException e) {
            log.network("Error while reading: " + e.toString());
            return null;
        }
    }

}
