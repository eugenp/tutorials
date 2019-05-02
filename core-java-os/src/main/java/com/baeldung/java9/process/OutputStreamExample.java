package com.baeldung.java9.process;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OutputStreamExample {

    public static void main(String[] args) {
        Logger log = Logger.getLogger(OutputStreamExample.class.getName());
        log.log(Level.INFO, Integer.toString(sum(1,2)));
    }

    public static int sum(int a, int b) {
        return a + b;
    }
}
