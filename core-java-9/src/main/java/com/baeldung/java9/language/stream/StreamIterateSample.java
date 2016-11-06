package com.baeldung.java9.language.stream;

import java.util.stream.Stream;

public class StreamIterateSample {

    public static void main(String[] args) {

        Stream.iterate(0, i -> i < 10, i -> i + 1)
              .forEach(System.out::println);

        for (int i = 0; i < 10; ++i) {
            System.out.println(i);
        }

    }

}
