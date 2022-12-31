package com.baeldung.generics;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CollectionUtilsDemo {

    private static final Logger logger = LoggerFactory.getLogger(CollectionUtilsDemo.class);

    public static void main(String[] args) {
        CollectionUtils.print("Baeldung");

        List<Number> numbers1 = new ArrayList<>();
        numbers1.add(5);
        numbers1.add(10L);

        List<Number> numbers2 = new ArrayList<>();
        numbers2.add(15f);
        numbers2.add(20.0);

        List<Number> numbersMerged = CollectionUtils.mergeTypeParameter(numbers1, numbers2);
        logger.info("Merged numbers: {}", numbersMerged);

        List<Number> numbers = new ArrayList<>();
        numbers.add(5);
        numbers.add(10L);
        numbers.add(15f);
        numbers.add(20.0);

        logger.info("Sum: {}", CollectionUtils.sum(numbers));
        logger.info("Sum (wildcard): {}", CollectionUtils.sumWildcard(numbers));
        logger.info("Sum (type parameter): {}", CollectionUtils.sumTypeParameter(numbers));

        List<Integer> integers = new ArrayList<>();
        integers.add(5);
        logger.info("Sum integers (wildcard): {}", CollectionUtils.sumWildcard(integers));

        CollectionUtils.addNumber(numbers, 4);
        CollectionUtils.addNumber(integers, 5);

        logger.info("Before swap: {}", numbers);
        CollectionUtils.swap(numbers, 0, 1);
        logger.info("After swap: {}", numbers);
    }
}
