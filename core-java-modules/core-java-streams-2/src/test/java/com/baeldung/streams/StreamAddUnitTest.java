package com.baeldung.streams;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

public class StreamAddUnitTest {

    @Test
    public void givenStream_whenAppendingObject_thenAppended() {
        Stream<String> anStream = Stream.of("a", "b", "c", "d", "e");

        Stream<String> newStream = Stream.concat(anStream, Stream.of("A"));

        List<String> resultList = newStream.collect(Collectors.toList());
        assertEquals(resultList.get(resultList.size() - 1), "A");
    }

    @Test
    public void givenStream_whenPrependingObject_thenPrepended() {
        Stream<Integer> anStream = Stream.of(1, 2, 3, 4, 5);

        Stream<Integer> newStream = Stream.concat(Stream.of(99), anStream);

        assertEquals(newStream.findFirst()
          .get(), (Integer) 99);
    }

    @Test
    public void givenStream_whenInsertingObject_thenInserted() {
        Stream<Double> anStream = Stream.of(1.1, 2.2, 3.3);

        Stream<Double> newStream = insertInStream(anStream, 9.9, 3);

        List<Double> resultList = newStream.collect(Collectors.toList());
        assertEquals(resultList.get(3), (Double) 9.9);
    }

    private static <T> Stream<T> insertInStream(Stream<T> stream, T elem, int index) {
        final Spliterator<T> spliterator = stream.spliterator();
        final Iterator<T> iterator = Spliterators.iterator(spliterator);
        
        return Stream.concat(Stream.concat(
          Stream.generate(iterator::next).limit(index),
          Stream.of(elem)),
          StreamSupport.stream(spliterator, /* parallel= */ false));
    }
}
