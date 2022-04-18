package com.baeldung.collections.iterable;

import java.util.Iterator;
import java.util.List;

public class IterableExample {

    public void iterateUsingIterator(List<Integer> numbers) {
        Iterator<Integer> iterator = numbers.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public void iterateUsingEnhancedForLoop(List<Integer> numbers) {
        for (Integer number : numbers) {
            System.out.println(number);
        }
    }

    public void iterateUsingForEachLoop(List<Integer> numbers) {
        numbers.forEach(System.out::println);
    }

    public void removeElementsUsingIterator(List<Integer> numbers) {
        Iterator<Integer> iterator = numbers.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
    }
}
