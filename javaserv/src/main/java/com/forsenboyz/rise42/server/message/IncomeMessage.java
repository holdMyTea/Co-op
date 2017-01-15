package com.forsenboyz.rise42.server.message;

import java.util.HashMap;
import java.util.Map;

class IncomeMessage {

    final int SOURCE;
    final String RAW;
    final int CODE;
    final Map<String, Float> PARAMS;

    IncomeMessage(String raw, int source){
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

    float getParam(String parameter){
        //System.out.println(RAW+" is getting params");
        //System.out.println("which are: "+PARAMS.toString());
        return PARAMS.get(parameter);
    }
}
