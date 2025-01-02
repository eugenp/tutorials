package com.baeldung.crunch;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.crunch.FilterFn;
import org.apache.crunch.PCollection;
import org.apache.crunch.impl.mem.MemPipeline;
import org.junit.Test;

import static org.junit.Assert.*;

public class StopWordFilterUnitTest {

    @Test
    public void givenFilter_whenStopWordPassed_thenFalseReturned() {
        FilterFn<String> filter = new StopWordFilter();

        assertFalse(filter.accept("the"));
    }

    @Test
    public void givenFilter_whenNonStopWordPassed_thenTrueReturned() {
        FilterFn<String> filter = new StopWordFilter();

        assertTrue(filter.accept("Hello"));
    }

    @Test
    public void givenWordCollection_whenFiltered_thenStopWordsRemoved() {
        PCollection<String> words = MemPipeline.collectionOf("This", "is", "a",
            "test", "sentence");

        PCollection<String> noStopWords = words.filter(new StopWordFilter());

        assertEquals(ImmutableList.of("This", "test", "sentence"),
            Lists.newArrayList(noStopWords.materialize()));
    }
}
