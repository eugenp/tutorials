package com.baeldung.stream;

import one.util.streamex.StreamEx;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class StreamExMergeStreamsTest {

    @Test
    public void givenTwoStreams_whenMergingStreams_thenResultingStreamContainsElementsFromBothStreams() {
        StreamEx<Integer> stream1 = StreamEx.of(1, 3, 5);
        StreamEx<Integer> stream2 = StreamEx.of(2, 4, 6);

        StreamEx<Integer> resultingStream = stream1.append(stream2);

        assertEquals(Arrays.asList(1, 3, 5, 2, 4, 6),
                     resultingStream.toList());
    }

    @Test
    public void givenFourStreams_whenMergingStreams_thenResultingStreamContainsElementsFromAllStreams() {
        StreamEx<Integer> stream1 = StreamEx.of(1, 3, 5);
        StreamEx<Integer> stream2 = StreamEx.of(2, 4, 6);
        StreamEx<Integer> stream3 = StreamEx.of(18, 15, 36);
        StreamEx<Integer> stream4 = StreamEx.of(99);

        StreamEx<Integer> resultingStream = stream1.append(stream2)
                                                   .append(stream3)
                                                   .append(stream4);

        assertEquals(Arrays.asList(1, 3, 5, 2, 4, 6, 18, 15, 36, 99),
                     resultingStream.toList());

    }

    @Test
    public void givenThreeStreams_whenAppendingAndPrependingStreams_thenResultingStreamContainsElementsFromAllStreams() {
        StreamEx<String> stream1 = StreamEx.of("foo", "bar");
        StreamEx<String> openingBracketStream = StreamEx.of("[");
        StreamEx<String> closingBracketStream = StreamEx.of("]");

        StreamEx<String> resultingStream = stream1.append(closingBracketStream)
                                                  .prepend(openingBracketStream);

        assertEquals(Arrays.asList("[", "foo", "bar", "]"),
                     resultingStream.toList());
    }
}
