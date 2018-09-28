package com.baeldung.stream.conditional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StreamForEachIfElseLogic {

    private static final Logger LOG = LoggerFactory.getLogger(StreamForEachIfElseLogic.class);

    public static void main(String[] args) {

        ifElseLogic();

    }

    private static void ifElseLogic() {

        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        ints.stream()
            .forEach(i -> {
                if (i.intValue() % 2 == 0) {
                    LOG.info("{} is even", i);
                } else {
                    LOG.info("{} is odd", i);
                }
            });

        Stream<Integer> evenIntegers = ints.stream()
            .filter(i -> i.intValue() % 2 == 0);
        Stream<Integer> oddIntegers = ints.stream()
            .filter(i -> i.intValue() % 2 != 0);

        evenIntegers.forEach(i -> LOG.info("{} is even", i));
        oddIntegers.forEach(i -> LOG.info("{} is odd", i));

    }
}
