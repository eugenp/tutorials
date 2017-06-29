package com.baeldung.outofmemoryerror.java_outofmemoryerror_demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OutOfMemoryGCLimitExceed { 
    public static void addRandomDataToMap() {
        Map<Integer, String> dataMap = new HashMap<Integer,String>();
        Random r = new Random();
        while (true) {
            dataMap.put(r.nextInt(), String.valueOf(r.nextInt()));
        }  
    }
}
