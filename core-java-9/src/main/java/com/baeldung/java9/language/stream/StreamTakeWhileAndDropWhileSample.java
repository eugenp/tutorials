package com.baeldung.java9.language.stream;

import java.util.stream.Stream;

public class StreamTakeWhileAndDropWhileSample {

    public static void main(String[] args) {

        Stream<String> stream = Stream.iterate("", s -> s + "s").takeWhile(s -> s.length() < 10);

        stream.dropWhile(s -> !s.contains("sssss"));

    }

}
