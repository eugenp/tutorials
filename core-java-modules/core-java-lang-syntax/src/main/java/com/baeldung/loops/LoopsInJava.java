package com.baeldung.loops;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoopsInJava {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoopsInJava.class);
    
    public int[] simple_for_loop() {
        int[] arr = new int[5];
        for (int i = 0; i < 5; i++) {
            arr[i] = i;
            LOGGER.debug("Simple for loop: i - " + i);
        }
        return arr;
    }

    public int[] enhanced_for_each_loop() {
        int[] intArr = { 0, 1, 2, 3, 4 };
        int[] arr = new int[5];
        for (int num : intArr) {
            arr[num] = num;
            LOGGER.debug("Enhanced for-each loop: i - " + num);
        }
        return arr;
    }

    public int[] while_loop() {
        int i = 0;
        int[] arr = new int[5];
        while (i < 5) {
            arr[i] = i;
            LOGGER.debug("While loop: i - " + i++);
        }
        return arr;
    }

    public int[] do_while_loop() {
        int i = 0;
        int[] arr = new int[5];
        do {
            arr[i] = i;
            LOGGER.debug("Do-While loop: i - " + i++);
        } while (i < 5);
        return arr;
    }
}