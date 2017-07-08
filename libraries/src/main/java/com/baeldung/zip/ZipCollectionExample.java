package com.baeldung.zip;

import com.google.common.collect.Streams;
import org.jooq.lambda.Seq;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ZipCollectionExample {
    static List<String> names = Arrays.asList("John", "Jane", "Jack", "Dennis");

    static List<Integer> ages = Arrays.asList(24, 25, 27);

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

        //      Using jOOL
        Seq
          .of("John", "Jane", "Dennis")
          .zip(Seq.of(24, 25, 27));

        Seq
          .of("John", "Jane", "Dennis")
          .zip(Seq.of(24, 25, 27), (x, y) -> x + ":" + y);

        Seq
          .of("a", "b", "c")
          .zipWithIndex();
    }
}
