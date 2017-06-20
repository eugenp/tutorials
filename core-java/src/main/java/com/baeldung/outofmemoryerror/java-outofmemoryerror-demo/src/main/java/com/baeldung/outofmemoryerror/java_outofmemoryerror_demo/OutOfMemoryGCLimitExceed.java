package com.baeldung.outofmemoryerror.java_outofmemoryerror_demo;

import java.util.Map;
import java.util.Random;

public class OutOfMemoryGCLimitExceed { 
    public static void putPropertiesToMap() {
        Map<Object, Object> map = System.getProperties();
        Random r = new Random();
        while (true) {
            map.put(r.nextInt(), "value");
        }  
    }
}
