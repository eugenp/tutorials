package com.baeldung.java8;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Consumer;

public class Java8ForEachUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(Java8ForEachUnitTest.class);

    @Test
    public void compareForEachMethods_thenPrintResults() {

        List<String> names = List.of("Larry", "Steve", "James", "Conan", "Ellen");

        // Java 5 - for-loop
        LOG.debug("--- Enhanced for-loop ---");
        for (String name : names) {
            LOG.info(name);
        }

        // Java 8 - forEach
        names.forEach(name -> {
            LOG.info(name);
        });

        LOG.debug("--- Print Consumer ---");
        Consumer<String> printConsumer = new Consumer<String>() {
            public void accept(String name) {
                LOG.info(name);
            }
        };

        names.forEach(printConsumer);

        // Anonymous inner class that implements Consumer interface
        LOG.debug("--- Anonymous inner class ---");
        names.forEach(new Consumer<String>() {
            public void accept(String name) {
                LOG.info(name);
            }
        });

        // Java 8 - forEach - Lambda Syntax
        LOG.debug("--- forEach method ---");
        names.forEach(name -> LOG.info(name));

        // Java 8 - forEach - Print elements using a Method Reference
        LOG.debug("--- Method Reference ---");
        names.forEach(LOG::info);
    }

    @Test
    public void givenList_thenIterateAndPrintResults() {
        List<String> names = List.of("Larry", "Steve", "James", "Conan", "Ellen");

        names.forEach(LOG::info);
    }

    @Test
    public void givenSet_thenIterateAndPrintResults() {
        Set<String> uniqueNames = new HashSet<>(Arrays.asList("Larry", "Steve", "James"));

        uniqueNames.forEach(LOG::info);
    }

    @Test
    public void givenQueue_thenIterateAndPrintResults() {
        Queue<String> namesQueue = new ArrayDeque<>(Arrays.asList("Larry", "Steve", "James"));

        namesQueue.forEach(LOG::info);
    }

    @Test
    public void givenMap_thenIterateAndPrintResults() {
        Map<Integer, String> namesMap = new HashMap<>();
        namesMap.put(1, "Larry");
        namesMap.put(2, "Steve");
        namesMap.put(3, "James");

        namesMap.entrySet()
            .forEach(entry -> LOG.info("{} {}", entry.getKey(), entry.getValue()));
    }

    @Test
    public void givenMap_whenUsingBiConsumer_thenIterateAndPrintResults2() {
        Map<Integer, String> namesMap = new HashMap<>();
        namesMap.put(1, "Larry");
        namesMap.put(2, "Steve");
        namesMap.put(3, "James");

        namesMap.forEach((key, value) -> LOG.info("{} {}", key, value));
    }

    @Test
    public void givenArray_whenIteratingWithForEachMethod_thenLogResult() {
        String[] foodItems = { "rice", "beans", "egg" };
        Arrays.stream(foodItems)
            .forEach(LOG::info);
    }

    @Test
    public void givenACollection_whenIteratingWithForEachInParallel_thenLogResult() {
        List<String> names = List.of("Larry", "Steve", "James", "Conan", "Ellen");
        names.parallelStream()
            .forEach(LOG::info);
    }
}
