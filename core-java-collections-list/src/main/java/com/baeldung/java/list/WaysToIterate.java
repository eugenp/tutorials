package com.baeldung.java.list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *  Demonstrates the different ways to loop over
 *  the elements of a list.
 */
public class WaysToIterate {

    List<String> countries = Arrays.asList("Germany", "Panama", "Australia");

    /**
     * Iterate over a list using a basic for loop
     */
    public void iterateWithForLoop() {
        for (int i = 0; i < countries.size(); i++) {
            System.out.println(countries.get(i));
        }
    }

    /**
     * Iterate over a list using the enhanced for loop
     */
    public void iterateWithEnhancedForLoop() {
        for (String country : countries) {
            System.out.println(country);
        }
    }

    /**
     * Iterate over a list using an Iterator
     */
    public void iterateWithIterator() {
        Iterator<String> countriesIterator = countries.iterator();
        while(countriesIterator.hasNext()) {
            System.out.println(countriesIterator.next());
        }
    }

    /**
     * Iterate over a list using a ListIterator
     */
    public void iterateWithListIterator() {
        ListIterator<String> listIterator = countries.listIterator();
        while(listIterator.hasNext()) {
            System.out.println(listIterator.next());
        }
    }

    /**
     * Iterate over a list using the Iterable.forEach() method
     */
    public void iterateWithForEach() {
        countries.forEach(System.out::println);
    }

    /**
     * Iterate over a list using the Stream.forEach() method
     */
    public void iterateWithStreamForEach() {
        countries.stream().forEach((c) -> System.out.println(c));
    }
}