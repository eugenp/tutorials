package com.baeldung.java.conversion;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.junit.Assert;
import org.junit.Test;

public class IterableStreamConversionTest {

    @Test
    public void givenIterable_whenConvertedToStream_thenTrue() {
        String[] names = { "Testing", "Iterable", "conversion", "to", "Stream" };
        StreamIterable<String> iterable = new StreamIterable<>(names);
        Assert.assertTrue(StreamSupport.stream(iterable.spliterator(), false) instanceof Stream<?>);
    }

    @Test
    public void whenConvertedToList_thenCorrect() {
        String[] names = { "Testing", "Iterable", "conversion", "to", "Stream" };
        StreamIterable<String> iterable = new StreamIterable<>(names);
        Stream<String> convertedStream = StreamSupport.stream(iterable.spliterator(), false);
        Assert.assertTrue(convertedStream.map(String::toUpperCase)
            .collect(Collectors.toList()) instanceof List<?>);
    }
}

class StreamIterable<T> implements Iterable<T> {
    private List<T> list;

    public StreamIterable(T[] array) {
        this.list = Arrays.asList(array);
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

}
