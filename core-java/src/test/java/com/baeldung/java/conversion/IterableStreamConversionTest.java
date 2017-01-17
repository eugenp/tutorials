package com.baeldung.java.conversion;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

public class IterableStreamConversionTest {

    @Test
    public void givenIterable_whenConvertedToStream_thenNotNull() {
        String[] names = {"Testing", "Iterable", "conversion", "to", "Stream"};
        StreamIterable<String> iterable = new StreamIterable<>(names);
        Assert.assertNotNull(StreamSupport.stream(iterable.spliterator(), false));
    }

    @Test
    public void whenConvertedToList_thenCorrect() {
        String[] names = {"Testing", "Iterable", "conversion", "to", "Stream"};
        StreamIterable<String> iterable = new StreamIterable<>(names);
        Stream<String> convertedStream = StreamSupport.stream(iterable.spliterator(), false);
        List<String> collected = convertedStream.map(String::toUpperCase).collect(Collectors.toList());
        assertThat(collected, contains("TESTING", "ITERABLE", "CONVERSION", "TO", "STREAM"));
    }
}

class StreamIterable<T> implements Iterable<T> {
    private List<T> list;

    StreamIterable(T[] array) {
        this.list = Arrays.asList(array);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

}
