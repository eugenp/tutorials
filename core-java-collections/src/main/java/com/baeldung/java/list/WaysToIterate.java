package baeldung.com.core;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *  Demonstrates the different ways to loop over
 *  the elements of a list.
 */
public class WaysToIterateList {

    List<String> countries = Arrays.asList("Germany", "Panama", "Australia");

    /**
     * Iterate over a list using a regular for loop
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
     * Iterate over a list using the forEach() method
     */
    public void iterateWithForEach() {
        countries.forEach(System.out::println);
    }

    /**
     * Iterate over a list using the ReverseListIterator
     * from the Apache Commons library.
     */
    public void iterateWithReverseListIterator() {
        ReverseListIterator reverseIterator = new ReverseListIterator(countries);
        while (reverseIterator.hasNext()) {
            System.out.println(reverseIterator.next());
        }
    }
}