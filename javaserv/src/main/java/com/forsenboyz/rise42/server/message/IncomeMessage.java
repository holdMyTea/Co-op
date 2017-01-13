package com.forsenboyz.rise42.server.message;

import java.util.HashMap;
import java.util.Map;

public class IncomeMessage {

    public final int SOURCE;
    public final String RAW;
    public final int CODE;
    public final Map<String, Float> PARAMS;

    public IncomeMessage(String raw, int source){
        this.RAW = raw;
        this.SOURCE = source;

        CODE = Integer.parseInt(raw.substring(1,2));

        //checking params presence
        if(raw.contains("(")) {

            PARAMS = new HashMap<String, Float>();
            String buff = raw.substring(raw.indexOf(':') + 1, raw.lastIndexOf('#'));
            //System.out.println("buff of "+RAW+" = "+buff);
            for (String s : buff.split(":")) {
                int bracket = s.indexOf('(');
                PARAMS.put(
                        s.substring(0, bracket),
                        Float.parseFloat(s.substring(bracket + 1, s.length() - 1))
                );
            }

        } else PARAMS = null;
    }

    public float getParam(IncomeParameters parameter){
        //System.out.println(RAW+" is getting params");
        //System.out.println("which are: "+PARAMS.toString());
        return PARAMS.get(parameter.name());
    }
}
