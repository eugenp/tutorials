package com.baeldung;


import org.jooq.lambda.Seq;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class JOOLTest {
    @Test
    public void givenSeq_whenCheckContains_shouldReturnTrue() {
        List<Integer> concat = Seq.of(1, 2, 3).concat(Seq.of(4, 5, 6)).toList();

        assertEquals(concat, Arrays.asList(1, 2, 3, 4, 5, 6));


        assertTrue(Seq.of(1, 2, 3, 4).contains(2));


        assertTrue(Seq.of(1, 2, 3, 4).containsAll(2, 3));


        assertTrue(Seq.of(1, 2, 3, 4).containsAny(2, 5));
    }
}
