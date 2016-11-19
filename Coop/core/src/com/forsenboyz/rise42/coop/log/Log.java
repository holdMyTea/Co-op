package com.forsenboyz.rise42.coop.log;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private static Log instance;

    private static final String DIR = "/home/rise42/Projects/Co-op/Coop/logs/";

    private Time time;
    private BufferedWriter writer;

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    private Log() {
        try {
            time = new Time("HH:mm:ss.SSS dd-MM-yyyy");
            writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(
                                    DIR + new SimpleDateFormat("HH-mm-ss dd-MM-yyyy").format(new Date()) + ".txt"
                            )
                    )
            );
        } catch (IOException ioEx) {
            System.out.println("Taking heavy casualties\n" + ioEx.toString());
        }
    }

    public void network(String msg) {
        try {
            msg = time.getTime()+": "+"NET"+": "+msg+"\n";
            System.out.print(msg);
            writer.write(msg);
            writer.flush();
        } catch (IOException e) {
            System.out.println("Taking heavy casualties");
        }
    }

}
