package com.baeldung.java8;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Java8ForEachUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(Java8ForEachUnitTest.class);

    @Test
    public void compareForEachMethods_thenPrintResults() {

        List<String> names = new ArrayList<>();
        names.add("Larry");
        names.add("Steve");
        names.add("James");
        names.add("Conan");
        names.add("Ellen");

        // Java 5 - for-loop
        LOG.debug("--- Enhanced for-loop ---");
        for (String name : names) {
            LOG.debug(name);
        }

        // Java 8 - forEach
        LOG.debug("--- forEach method ---");
        names.forEach(name -> LOG.debug(name));

        // Anonymous inner class that implements Consumer interface
        LOG.debug("--- Anonymous inner class ---");
        names.forEach(new Consumer<String>() {
            public void accept(String name) {
                LOG.debug(name);
            }
        });

        // Create a Consumer implementation to then use in a forEach method
        Consumer<String> consumerNames = name -> {
            LOG.debug(name);
        };
        LOG.debug("--- Implementation of Consumer interface ---");
        names.forEach(consumerNames);

        // Print elements using a Method Reference
        LOG.debug("--- Method Reference ---");
        names.forEach(LOG::debug);

    }

}
