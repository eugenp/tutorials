package com.baeldung.highcpu;

import java.util.LinkedList;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class Application {

    static List<Integer> generateList() {
        return IntStream.range(0, 10000000).parallel().map(IntUnaryOperator.identity()).collect(LinkedList::new, List::add, List::addAll);
    }

    public static void main(String[] args) {
        List<Integer> list = generateList();
        long start = System.nanoTime();
        int value = list.get(9500000);
        System.out.printf("Found value %d in %d nanos\n", value, (System.nanoTime() - start));
    }
}
