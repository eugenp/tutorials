package com.baeldung.guava.tutorial;

import com.google.common.collect.MoreCollectors;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MoreCollectorsExample {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1);
        Optional<Integer> number = numbers
          .stream()
          .map(e -> e * 2)
          .collect(MoreCollectors.toOptional());
    }
}
