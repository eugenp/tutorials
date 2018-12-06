package com.baeldung.synchronizedcollections.application;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class Application {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getName());
    
    public static void main(String[] args) throws InterruptedException {
        List<Integer> syncCollection = Collections.synchronizedList(Arrays.asList(1, 2, 3, 4, 5, 6));
        synchronized (syncCollection) {
            syncCollection.forEach((e) -> {LOGGER.info(e.toString());});
        }
    }
}
