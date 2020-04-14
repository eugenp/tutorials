package com.baeldung;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

public class Main {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("Dallas", "Texas");
        map.put("Green Bay", "Wisconsin");
        map.put("Seattle", "Washington");

        LoggerFactory.getLogger(Main.class)
            .info("Map: {}", map);
    }

}
