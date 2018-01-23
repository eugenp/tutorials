package com.baeldung.dependencyinjection.constructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuickSortAlgorithm implements SortAlgorithm {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(QuickSortAlgorithm.class);

    public int[] sort(int[] inputArray) {
        // Sort the input array
        int[] sortedArray = {};
        LOGGER.info("Executing Quick Sort algorithm.");
        return sortedArray;
    }
}
