/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.nullsafecollectionstreams;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

import org.junit.Test;

/**
 *
 * @author Kwaje Anthony <kwajeanthony@gmail.com>
 */
public class NullSafeCollectionStreamsUsingJava8OptionalContainerUnitTest {

    private final NullSafeCollectionStreamsUsingJava8OptionalContainer instance = new NullSafeCollectionStreamsUsingJava8OptionalContainer();

    @Test
    public void whenCollectionIsNull_thenExpectAnEmptyStream() {
        Collection<String> collection = null;
        Stream<String> expResult = Stream.empty();
        Stream<String> result = instance.collectionAsStream(collection);
        assertStreamEquals(expResult, result);

    }

    @Test
    public void whenCollectionHasElements_thenExpectAStreamOfExactlyTheSameElements() {

        Collection<String> collection = Arrays.asList("a", "b", "c");
        Stream<String> expResult = Arrays.stream(new String[] { "a", "b", "c" });
        Stream<String> result = instance.collectionAsStream(collection);
        assertStreamEquals(expResult, result);
    }

    private static void assertStreamEquals(Stream<?> s1, Stream<?> s2) {
        Iterator<?> iter1 = s1.iterator(), iter2 = s2.iterator();
        while (iter1.hasNext() && iter2.hasNext())
            assertEquals(iter1.next(), iter2.next());
        assert !iter1.hasNext() && !iter2.hasNext();
    }

}
