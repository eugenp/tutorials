package com.baeldung.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class InfiniteStreams {

    private static final Logger LOG = LoggerFactory.getLogger(InfiniteStreams.class);

    public static void main(String[] args) {
        doWhileOldWay();

        doWhileStreamWay();

    }

    private static void doWhileOldWay() {

        int i = 0;
        while (i < 10) {
            LOG.debug("{}", i);
            i++;
        }
    }

    private static void doWhileStreamWay() {
        Stream<Integer> integers = Stream.iterate(0, i -> i + 1);
        integers.limit(10).forEach(System.out::println);
    }
}
