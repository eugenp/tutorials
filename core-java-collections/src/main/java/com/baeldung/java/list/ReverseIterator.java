package com.baeldung.java.list;

import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.collections4.iterators.ReverseListIterator;

import com.google.common.collect.Lists;

/**
 * Provides methods for iterating backward over a list.
 */
public class ReverseIterator {

    /**
     * Iterate using the for loop.
     *
     * @param list the list
     */
    public void iterateUsingForLoop(final List<String> list) {

        for (int i = list.size(); i-- > 0; ) {
            System.out.println(list.get(i));
        }
    }

    /**
     * Iterate using the Java {@link ListIterator}.
     *
     * @param list the list
     */
    public void iterateUsingListIterator(final List<String> list) {

        final ListIterator listIterator = list.listIterator(list.size());
        while (listIterator.hasPrevious()) {
            System.out.println(listIterator.previous());
        }
    }

    /**
     * Iterate using Java {@link Collections} API.
     *
     * @param list the list
     */
    public void iterateUsingCollections(final List<String> list) {

        Collections.reverse(list);
        for (final String item : list) {
            System.out.println(item);
        }
    }

    /**
     * Iterate using Apache Commons {@link ReverseListIterator}.
     *
     * @param list the list
     */
    public void iterateUsingApacheReverseListIterator(final List<String> list) {

        final ReverseListIterator listIterator = new ReverseListIterator(list);
        while (listIterator.hasNext()) {
            System.out.println(listIterator.next());
        }
    }

    /**
     * Iterate using Guava {@link Lists} API.
     *
     * @param list the list
     */
    public void iterateUsingGuava(final List<String> list) {

        final List<String> reversedList = Lists.reverse(list);
        for (final String item : reversedList) {
            System.out.println(item);
        }
    }

}
