package com.baeldung.stream;

import org.jooq.lambda.Seq;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class JoolMergeStreamsTest {
    @Test
    public void givenTwoStreams_whenMergingStreams_thenResultingStreamContainsElementsFromBothStreams() {
        Seq<Integer> seq1 = Seq.of(1, 3, 5);
        Seq<Integer> seq2 = Seq.of(2, 4, 6);

        Seq<Integer> resultingSeq = seq1.append(seq2);

        assertEquals(Arrays.asList(1, 3, 5, 2, 4, 6),
                     resultingSeq.toList());
    }

    @Test
    public void givenThreeStreams_whenAppendingAndPrependingStreams_thenResultingStreamContainsElementsFromAllStreams() {
        Seq<String> seq = Seq.of("foo", "bar");
        Seq<String> openingBracketSeq = Seq.of("[");
        Seq<String> closingBracketSeq = Seq.of("]");

        Seq<String> resultingStream = seq.append(closingBracketSeq)
                                         .prepend(openingBracketSeq);

        Assert.assertEquals(Arrays.asList("[", "foo", "bar", "]"),
                            resultingStream.toList());
    }
}