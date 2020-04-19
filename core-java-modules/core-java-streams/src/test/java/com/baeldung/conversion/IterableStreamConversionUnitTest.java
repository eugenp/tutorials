package com.baeldung.conversion;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class IterableStreamConversionUnitTest {

    @Test
    public void givenIterable_whenConvertedToStream_thenNotNull() {
        Iterable<String> iterable = Arrays.asList("Testing", "Iterable", "conversion", "to", "Stream");

        Assert.assertNotNull(StreamSupport.stream(iterable.spliterator(), false));
    }

    @Test
    public void whenConvertedToList_thenCorrect() {
        Iterable<String> iterable = Arrays.asList("Testing", "Iterable", "conversion", "to", "Stream");

        List<String> result = StreamSupport.stream(iterable.spliterator(), false).map(String::toUpperCase).collect(Collectors.toList());

        assertThat(result, contains("TESTING", "ITERABLE", "CONVERSION", "TO", "STREAM"));
    }
}
