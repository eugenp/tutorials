package com.baeldung.guava.tutorial;

import com.google.common.collect.Streams;

import java.util.stream.Stream;

public class ConcatStreams {
    public static Stream concatStreams(Stream stream1, Stream stream2, Stream stream3) {
        return Streams.concat(stream1, stream2, stream3);
    }
}
