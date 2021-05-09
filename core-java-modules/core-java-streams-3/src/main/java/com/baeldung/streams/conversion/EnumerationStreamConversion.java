package com.baeldung.streams.conversion;

import java.util.Enumeration;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class EnumerationStreamConversion {

    public static <T> Stream<T> convert(Enumeration<T> enumeration) {
        EnumerationSpliterator<T> spliterator = new EnumerationSpliterator<T>(Long.MAX_VALUE, Spliterator.ORDERED, enumeration);
        Stream<T> stream = StreamSupport.stream(spliterator, false);

        return stream;
    }
}
