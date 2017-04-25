package com.baeldung.java.set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class SetTest {

    @Test
    public void givenTreeSet_whenRetrievesObjects_thenNaturalOrder() {
        Set<String> set = new TreeSet<>();
        set.add("Baeldung");
        set.add("is");
        set.add("Awesome");
        assertEquals(3, set.size());
        assertTrue(set.iterator()
            .next()
            .equals("Awesome"));
    }

    @Test(expected = NullPointerException.class)
    public void givenTreeSet_whenAddNullObject_thenNullPointer() {
        Set<String> set = new TreeSet<>();
        set.add("Baeldung");
        set.add("is");
        set.add(null);
    }

    @Test
    public void givenHashSet_whenAddNullObject_thenOK() {
        Set<String> set = new HashSet<>();
        set.add("Baeldung");
        set.add("is");
        set.add(null);
        assertEquals(3, set.size());
    }

    @Test
    public void givenHashSetAndTreeSet_whenAddObjects_thenHashSetIsFaster() {
        Set<String> set = new HashSet<>();
        long startTime = System.nanoTime();
        set.add("Baeldung");
        set.add("is");
        set.add("Awesome");
        long endTime = System.nanoTime();
        long duration1 = (endTime - startTime);

        Set<String> set2 = new TreeSet<>();
        startTime = System.nanoTime();
        set2.add("Baeldung");
        set2.add("is");
        set2.add("Awesome");
        endTime = System.nanoTime();
        long duration2 = (endTime - startTime);
        assertTrue(duration1 < duration2);
    }

    @Test
    public void givenHashSetAndTreeSet_whenAddDuplicates_thenOnlyUnique() {
        Set<String> set = new HashSet<>();
        set.add("Baeldung");
        set.add("Baeldung");
        assertTrue(set.size() == 1);

        Set<String> set2 = new TreeSet<>();
        set2.add("Baeldung");
        set2.add("Baeldung");
        assertTrue(set2.size() == 1);
    }

    @Test(expected = ConcurrentModificationException.class)
    public void givenHashSet_whenModifyWhenIterator_thenFailFast() {
        Set<String> set = new HashSet<>();
        set.add("Baeldung");
        Iterator<String> it = set.iterator();

        while (it.hasNext()) {
            set.add("Awesome");
            it.next();
        }
    }
}
