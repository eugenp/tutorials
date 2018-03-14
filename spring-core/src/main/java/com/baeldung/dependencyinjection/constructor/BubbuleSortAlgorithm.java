package com.baeldung.dependencyinjection.constructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BubbuleSortAlgorithm implements SortAlgorithm {

    private static final Logger LOGGER = LoggerFactory.getLogger(BubbuleSortAlgorithm.class);

    public int[] sort(int[] inputArray) {
        // Sort the input array\
        int[] sortedArray = {};
        return sortedArray;
    }

}
