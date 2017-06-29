package com.baeldung.guava.tutorial;

import com.google.common.collect.Streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class StreamsZipExample {
    static List<String> names = new ArrayList<>(Arrays.asList("John", "Jane", "Jack", "Dennis"));

    static List<Integer> ages = new ArrayList<>(Arrays.asList(24, 25, 27));

    public static void main(String[] args) {
        //      Using Streams API from Guava 21
        Streams
          .zip(names.stream(), ages.stream(), (name, age) -> name + ":" + age)
          .forEach(System.out::println);

        //        Using native Java 8 Int Stream
        IntStream
          .range(0, Math.min(names.size(), ages.size()))
          .mapToObj(i -> names.get(i) + ":" + ages.get(i))
          .forEach(System.out::println);
        }
}
