package com.forsenboyz.rise42.server.message;

import com.forsenboyz.rise42.server.network.Connection;

public class OutcomeMessage {

    public final int SOURCE;

    public final int CODE;

    private String response;


    public OutcomeMessage(int source, int code){
        this.SOURCE = source;
        this.CODE = code;
        this.response = Integer.toString(code);
    }

    public void addParameter(Parameters parameter, float value){
        this.response += ":"+parameter + "("+value+")";
    }

    public String getSourceResponse() {
        return "r"+response+";";
    }

    public String getOthersResponse() {
        return "s"+response+";";
    }

    @Override
    public String toString(){
        return getOthersResponse();
    }
}
