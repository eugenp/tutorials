package com.baeldung.guava.tutorial;

import com.google.common.collect.Streams;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamsUtility {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        //Using Collection
        Stream<Integer> streamFromCollection = Streams.stream(numbers);
        //Using Iterator
        Stream<Integer> streamFromIterator = Streams.stream(numbers.iterator());
        //Using Iterable
        Stream<Integer> streamFromIterable = Streams.stream((Iterable<Integer>) numbers);
        //Using Optional
        Stream<Integer> streamFromOptional = Streams.stream(Optional.of(1));
        //Using OptionalLong to LongStream
        LongStream streamFromOptionalLong = Streams.stream(OptionalLong.of(1));
        //Using OptionalInt to IntStream
        IntStream streamFromOptionalInt = Streams.stream(OptionalInt.of(1));
        //Using OptionalDouble to DoubleStream
        DoubleStream streamFromOptionalDouble = Streams.stream(OptionalDouble.of(1.0));

        Stream<Integer> concatenatedStreams = Streams.concat(streamFromCollection, streamFromIterable, streamFromIterator);

        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        //This will return 10
        Optional<Integer> lastItem = Streams.findLast(integers.stream());

        Streams.zip(Stream.of("candy", "chocolate", "bar"), Stream.of("$1", "$2", "$3"), (arg1, arg2) -> arg1 + ":" + arg2);
    }
}
