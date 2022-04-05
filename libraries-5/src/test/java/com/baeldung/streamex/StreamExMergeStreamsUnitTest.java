package com.baeldung.streamex;

import one.util.streamex.StreamEx;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class StreamExMergeStreamsUnitTest {

    @Test
    public void givenTwoStreams_whenMergingStreams_thenResultingStreamContainsElementsFromBothStreams() {
        Stream<Integer> stream1 = Stream.of(1, 3, 5);
        Stream<Integer> stream2 = Stream.of(2, 4, 6);

        Stream<Integer> resultingStream = StreamEx.of(stream1).append(stream2);

        assertEquals(Arrays.asList(1, 3, 5, 2, 4, 6), resultingStream.collect(Collectors.toList()));
    }

    @Test
    public void givenFourStreams_whenMergingStreams_thenResultingStreamContainsElementsFromAllStreams() {
        Stream<Integer> stream1 = Stream.of(1, 3, 5);
        Stream<Integer> stream2 = Stream.of(2, 4, 6);
        Stream<Integer> stream3 = Stream.of(18, 15, 36);
        Stream<Integer> stream4 = Stream.of(99);

        Stream<Integer> resultingStream = StreamEx.of(stream1).append(stream2).append(stream3).append(stream4);

        assertEquals(Arrays.asList(1, 3, 5, 2, 4, 6, 18, 15, 36, 99), resultingStream.collect(Collectors.toList()));

    }

    @Test
    public void givenThreeStreams_whenAppendingAndPrependingStreams_thenResultingStreamContainsElementsFromAllStreams() {
        Stream<String> stream1 = Stream.of("foo", "bar");
        Stream<String> openingBracketStream = Stream.of("[");
        Stream<String> closingBracketStream = Stream.of("]");

        Stream<String> resultingStream = StreamEx.of(stream1).append(closingBracketStream).prepend(openingBracketStream);

        assertEquals(Arrays.asList("[", "foo", "bar", "]"), resultingStream.collect(Collectors.toList()));
    }
}
