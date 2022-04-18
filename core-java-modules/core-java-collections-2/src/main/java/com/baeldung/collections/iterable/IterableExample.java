package com.baeldung.collections.iterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class IterableExample {

    public static void iterateUsingIterator() {
        List<Integer> numbers = getNumbers();

        Iterator<Integer> iterator = numbers.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public static void removeElementsUsingIterator() {
        List<Integer> numbers = getNumbers();

        Iterator<Integer> iterator = numbers.iterator();
        while (iterator.hasNext()) {
            iterator.remove();
        }
    }

    public static void iterateUsingEnhancedForLoop() {
        List<Integer> numbers = getNumbers();

        for (Integer number : numbers) {
            System.out.println(number);
        }
    }

    public static void iterateUsingForEachLoop() {
        List<Integer> numbers = getNumbers();
        numbers.forEach(System.out::println);
    }

    private static List<Integer> getNumbers() {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);
        numbers.add(40);
        return numbers;
    }
}
