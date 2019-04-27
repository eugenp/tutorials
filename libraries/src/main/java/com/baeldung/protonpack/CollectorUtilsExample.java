package com.baeldung.protonpack;

import java.util.Optional;
import java.util.stream.Stream;

import static com.codepoetics.protonpack.collectors.CollectorUtils.maxBy;
import static com.codepoetics.protonpack.collectors.CollectorUtils.minBy;

public class CollectorUtilsExample {

    public void minMaxProjectionCollector() {
        Stream<String> integerStream = Stream.of("a", "bb", "ccc", "1");

        Optional<String> max = integerStream.collect(maxBy(String::length));
        Optional<String> min = integerStream.collect(minBy(String::length));

    }
}
