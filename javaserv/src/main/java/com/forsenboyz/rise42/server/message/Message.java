package com.forsenboyz.rise42.server.message;

import com.forsenboyz.rise42.server.network.Connection;

public class Message {

    public final int SOURCE;

    private String sourceResponse;
    private String othersResponse;


    public Message(int source, String sourceResponse, String othersResponse){
        this.SOURCE = source;
        this.sourceResponse = sourceResponse;
        this.othersResponse = othersResponse;
    }

    public String getSourceResponse() {
        return sourceResponse;
    }

    public String getOthersResponse() {
        return othersResponse;
    }
}
