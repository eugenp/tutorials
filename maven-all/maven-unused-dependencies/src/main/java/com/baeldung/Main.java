package com.baeldung;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

public class Main {

    public static void main(String[] args) {
        Map<String, String> teamMap = new HashMap<>();
        teamMap.put("Dallas", "Cowboys");
        teamMap.put("Green Bay", "Packers");
        teamMap.put("Washington", "Redskins");

        LoggerFactory.getLogger(Main.class)
            .info("Team Map: {}", teamMap);

    }
}
