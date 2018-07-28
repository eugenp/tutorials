/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.nullsafecollectionstreams;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Kwaje Anthony <kwajeanthony@gmail.com>
 */
public class NullSafeCollectionStreamsTest {

    public NullSafeCollectionStreamsTest() {
    }

    /**
     * Test making created collection streams null safe through the use of a check to prevent null dereferences
     */
    @Test
    public void testCollectionAsStream1() {
        System.out.println("collectionAsStream1");

        // Test with a non-null collection
        Collection<String> collection = Arrays.asList("a", "b", "c");
        NullSafeCollectionStreams instance = new NullSafeCollectionStreams();
        Stream<String> expResult = Arrays.stream(new String[] { "a", "b", "c" });
        Stream<String> result = instance.collectionAsStream1(collection);
        assertStreamEquals(expResult, result);

        // Test with a null collection
        collection = null;
        expResult = Stream.empty();
        result = instance.collectionAsStream1(collection);
        assertStreamEquals(expResult, result);

    }

    /**
     * Test making created collection streams null safe through the use of emptyIfNull() method from Apache 
     * Commons CollectionUtils library
     */
    @Test
    public void testCollectionAsStream2() {
        System.out.println("collectionAsStream2");

        // Test with a non-null collection
        Collection<String> collection = Arrays.asList("a", "b", "c");
        NullSafeCollectionStreams instance = new NullSafeCollectionStreams();
        Stream<String> expResult = Arrays.stream(new String[] { "a", "b", "c" });
        Stream<String> result = instance.collectionAsStream2(collection);
        assertStreamEquals(expResult, result);

        // Test with a null collection
        collection = null;
        expResult = Stream.empty();
        result = instance.collectionAsStream2(collection);
        assertStreamEquals(expResult, result);
    }

    /**
     * Test making created collection streams null safe through the use of Java SE 8’s Optional Container
     */
    @Test
    public void testCollectionAsStream3() {
        System.out.println("collectionAsStream3");

        // Test with a non-null collection
        Collection<String> collection = Arrays.asList("a", "b", "c");
        NullSafeCollectionStreams instance = new NullSafeCollectionStreams();
        Stream<String> expResult = Arrays.stream(new String[] { "a", "b", "c" });
        Stream<String> result = instance.collectionAsStream3(collection);
        assertStreamEquals(expResult, result);

        // Test with a null collection
        collection = null;
        expResult = Stream.empty();
        result = instance.collectionAsStream3(collection);
        assertStreamEquals(expResult, result);
    }

    private static void assertStreamEquals(Stream<?> s1, Stream<?> s2) {
        Iterator<?> iter1 = s1.iterator(), iter2 = s2.iterator();
        while (iter1.hasNext() && iter2.hasNext())
            assertEquals(iter1.next(), iter2.next());
        assert !iter1.hasNext() && !iter2.hasNext();
    }

}
