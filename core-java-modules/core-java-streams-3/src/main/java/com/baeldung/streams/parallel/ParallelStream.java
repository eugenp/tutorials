package com.baeldung.streams.parallel;

import java.util.List;

public class ParallelStream {

    public static void main(String[] args) {
        List<Integer> listOfNumbers = List.of(1, 2, 3, 4);
        listOfNumbers.parallelStream().forEach(number ->
          System.out.println(number + " " + Thread.currentThread().getName())
        );
    }

}
