package com.baeldung.streams.parallel;

import java.util.Arrays;
import java.util.List;

public class ParallelStream {

    public static void main(String[] args) {
        List<Integer> listOfNumbers = Arrays.asList(1, 2, 3, 4);
        listOfNumbers.parallelStream().forEach(number ->
          System.out.println(number + " " + Thread.currentThread().getName())
        );
    }

}
