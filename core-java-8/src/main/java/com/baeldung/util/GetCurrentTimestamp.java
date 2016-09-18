package com.baeldung.util;

import java.time.Instant;

public class GetCurrentTimestamp {

    public static void main(String args[]) {
        
        Instant instant = Instant.now();
        System.out.println("Current timestamp is: " + instant.toEpochMilli());

        System.out.println("Number of seconds: " + instant.getEpochSecond());
    }
}
