package com.baeldung.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class MergeStreamsUnitTest {

    @Test
    public void givenTwoStreams_whenMergingStreams_thenResultingStreamContainsElementsFromBothStreams() {
        Stream<Integer> stream1 = Stream.of(1, 3, 5);
        Stream<Integer> stream2 = Stream.of(2, 4, 6);

        Stream<Integer> resultingStream = Stream.concat(stream1, stream2);

        assertEquals(Arrays.asList(1, 3, 5, 2, 4, 6), resultingStream.collect(Collectors.toList()));
    }

    @Test
    public void givenThreeStreams_whenMergingStreams_thenResultingStreamContainsElementsFromAllStreams() {
        Stream<Integer> stream1 = Stream.of(1, 3, 5);
        Stream<Integer> stream2 = Stream.of(2, 4, 6);
        Stream<Integer> stream3 = Stream.of(18, 15, 36);

        Stream<Integer> resultingStream = Stream.concat(Stream.concat(stream1, stream2), stream3);

        assertEquals(Arrays.asList(1, 3, 5, 2, 4, 6, 18, 15, 36), resultingStream.collect(Collectors.toList()));
    }

    @Test
    public void givenFourStreams_whenMergingStreams_thenResultingStreamContainsElementsFromAllStreams() {
        Stream<Integer> stream1 = Stream.of(1, 3, 5);
        Stream<Integer> stream2 = Stream.of(2, 4, 6);
        Stream<Integer> stream3 = Stream.of(18, 15, 36);
        Stream<Integer> stream4 = Stream.of(99);

        Stream<Integer> resultingStream = Stream.of(stream1, stream2, stream3, stream4).flatMap(Function.identity());

        assertEquals(Arrays.asList(1, 3, 5, 2, 4, 6, 18, 15, 36, 99), resultingStream.collect(Collectors.toList()));

    }
}