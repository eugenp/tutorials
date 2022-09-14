package com.baeldung.openltablets.rules;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private String result;
    private Map<String, String> map = new HashMap<>();
    
    public Response() { }
    
    public String getResult() {
        return result;
    }

    public void setResult(final String s) {
        result = s;
    }

    public Map<String, String> getMap() {
        return map;
    }

}
