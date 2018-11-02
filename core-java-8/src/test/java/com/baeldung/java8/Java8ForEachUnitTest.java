package com.baeldung.java8;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        names.forEach(name -> {
            System.out.println(name);
        });

        LOG.debug("--- Print Consumer ---");
        Consumer<String> printConsumer = new Consumer<String>() {
            public void accept(String name) {
                System.out.println(name);
            };
        };

        names.forEach(printConsumer);

        // Anonymous inner class that implements Consumer interface
        LOG.debug("--- Anonymous inner class ---");
        names.forEach(new Consumer<String>() {
            public void accept(String name) {
                LOG.debug(name);
            }
        });

        // Java 8 - forEach - Lambda Syntax
        LOG.debug("--- forEach method ---");
        names.forEach(name -> LOG.debug(name));

        // Java 8 - forEach - Print elements using a Method Reference
        LOG.debug("--- Method Reference ---");
        names.forEach(LOG::debug);
    }

    @Test
    public void givenList_thenIterateAndPrintResults() {
        List<String> names = Arrays.asList("Larry", "Steve", "James");

        names.forEach(System.out::println);
    }

    @Test
    public void givenSet_thenIterateAndPrintResults() {
        Set<String> uniqueNames = new HashSet<>(Arrays.asList("Larry", "Steve", "James"));

        uniqueNames.forEach(System.out::println);
    }

    @Test
    public void givenQueue_thenIterateAndPrintResults() {
        Queue<String> namesQueue = new ArrayDeque<>(Arrays.asList("Larry", "Steve", "James"));

        namesQueue.forEach(System.out::println);
    }

    @Test
    public void givenMap_thenIterateAndPrintResults() {
        Map<Integer, String> namesMap = new HashMap<>();
        namesMap.put(1, "Larry");
        namesMap.put(2, "Steve");
        namesMap.put(3, "James");

        namesMap.entrySet()
            .forEach(entry -> System.out.println(entry.getKey() + " " + entry.getValue()));
    }

    @Test
    public void givenMap_whenUsingBiConsumer_thenIterateAndPrintResults2() {
        Map<Integer, String> namesMap = new HashMap<>();
        namesMap.put(1, "Larry");
        namesMap.put(2, "Steve");
        namesMap.put(3, "James");

        namesMap.forEach((key, value) -> System.out.println(key + " " + value));
    }

}
