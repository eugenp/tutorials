package com.baeldung.dependencyinjection.setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BinarySearch {

    private static final Logger LOGGER = LoggerFactory.getLogger(BinarySearch.class);
    private SortAlgorithm sortAlgorithm;

    @Autowired
    public void setSortAlgorithm(SortAlgorithm sortAlgorithm) {
        this.sortAlgorithm = sortAlgorithm;
    }

    public int binarySearch(int[] numbers, int numberToBeSearched) {
        int index = 0;
        // Sort the input array
        // Search the number in the array
        // Get the index
        LOGGER.info("Executing Bubble Sort algorithm.");
        return index;
    }

}
