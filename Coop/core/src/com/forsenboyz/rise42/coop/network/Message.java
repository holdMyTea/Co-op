package com.forsenboyz.rise42.coop.network;

import java.util.HashMap;
import java.util.Map;

public class Message {

    private final String RAW;

    private final boolean RESPONSE;
    private final String CODE;

    private Map<String, Float> PARAMS;

    Message(String rawMessage){
        this.RAW = rawMessage;

        RESPONSE = rawMessage.charAt(0) == 'r';
        CODE = rawMessage.substring(1,2);

        //checking params presence
        if(rawMessage.contains("(")) {

            PARAMS = new HashMap<String, Float>();
            String buff = rawMessage.substring(rawMessage.indexOf(':') + 1, rawMessage.length() - 1);
            for (String s : buff.split(":")) {
                int bracket = s.indexOf('(');
                PARAMS.put(
                        s.substring(0, bracket),
                        Float.parseFloat(s.substring(bracket + 1, s.length() - 1))
                );
            }

        }
    }

    @Override
    public String toString(){
        return RAW;
    }

    public boolean isResponse() {
        return RESPONSE;
    }

    public String getCode() {
        return CODE;
    }

    public Map<String, Float> getParams() {
        return PARAMS;
    }
}
