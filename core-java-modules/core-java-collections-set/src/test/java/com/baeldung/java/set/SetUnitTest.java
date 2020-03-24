package com.baeldung.java.set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SetUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(SetUnitTest.class);

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

        long hashSetInsertionTime = measureExecution(() -> {
            Set<String> set = new HashSet<>();
            set.add("Baeldung");
            set.add("is");
            set.add("Awesome");
        });

        long treeSetInsertionTime = measureExecution(() -> {
            Set<String> set = new TreeSet<>();
            set.add("Baeldung");
            set.add("is");
            set.add("Awesome");
        });

        LOG.debug("HashSet insertion time: {}", hashSetInsertionTime);
        LOG.debug("TreeSet insertion time: {}", treeSetInsertionTime);
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

    private static long measureExecution(Runnable task) {
        long startTime = System.nanoTime();
        task.run();
        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;
        LOG.debug(String.valueOf(executionTime));
        return executionTime;
    }
}
