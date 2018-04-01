package com.coreservlets.demo2;

import java.util.HashMap;
import java.util.Map;

public class Hello {
    private Map<String, String> messages;

    public Hello() {
        messages = new HashMap<String, String>();
        messages.put("one", "1one");
        messages.put("two", "2two");
    }

    public String doHello(String key) {
        String message = messages.get(key);
        return "key: " + key + ",value: " + message + "!";
    }
}
