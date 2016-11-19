package com.forsenboyz.rise42.coop.log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

    private SimpleDateFormat dateFormat;

    public Time(String template){
        dateFormat = new SimpleDateFormat(template);
    }

    public String getTime(){
        return dateFormat.format(new Date());
    }
}
