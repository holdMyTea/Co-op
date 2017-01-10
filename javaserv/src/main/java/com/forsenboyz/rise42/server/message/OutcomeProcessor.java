package com.forsenboyz.rise42.server.message;

import com.forsenboyz.rise42.server.objects.ObjectHolder;

import java.util.ArrayDeque;
import java.util.Collection;

import static com.forsenboyz.rise42.server.message.Codes.*;

public class OutcomeProcessor {

    private ObjectHolder objectHolder;

    private ArrayDeque<OutcomeMessage> messages;

    public OutcomeProcessor(ObjectHolder objectHolder){
        this.objectHolder = objectHolder;
    }

    public void makeHeroMessages(){

    }

    public void makeProjectileMessages(){

    }

    public ArrayDeque<OutcomeMessage> getMessages() {
        return messages;
    }
}
