package com.baeldung.streams.primitivestreams;

import java.util.Arrays;
import java.util.stream.IntStream;

class PrimitiveStreams {

    int min(int[] integers) {
        return Arrays.stream(integers).min().getAsInt();
    }

    int max(int... integers) {
        return IntStream.of(integers).max().getAsInt();
    }

    int sum(int... integers) {
        return IntStream.of(integers).sum();
    }

    double avg(int... integers) {
        return IntStream.of(integers).average().getAsDouble();
    }
}
