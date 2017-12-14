package com.baeldung.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

public class WhenUsingTreeSet {

    @Test
    public void whenAddingElement_shouldAddElement() {
        Set<String> treeSet = new TreeSet<>();
        Assert.assertTrue(treeSet.add("String Added"));
    }

    @Test
    public void whenCheckingForElement_shouldSearchForElement() {
        Set<String> treeSetContains = new TreeSet<>();
        treeSetContains.add("String Added");
        Assert.assertTrue(treeSetContains.contains("String Added"));
    }

    @Test
    public void whenRemovingElement_shouldRemoveElement() {
        Set<String> removeFromTreeSet = new TreeSet<>();
        removeFromTreeSet.add("String Added");
        Assert.assertTrue(removeFromTreeSet.remove("String Added"));
    }

    @Test
    public void whenClearingTreeSet_shouldClearTreeSet() {
        Set<String> clearTreeSet = new TreeSet<>();
        clearTreeSet.add("String Added");
        clearTreeSet.clear();
        Assert.assertTrue(clearTreeSet.isEmpty());
    }

    @Test
    public void whenCheckingTheSizeOfTreeSet_shouldReturnThesize() {
        Set<String> treeSetSize = new TreeSet<>();
        treeSetSize.add("String Added");
        Assert.assertEquals(1, treeSetSize.size());
    }

    @Test
    public void whenCheckingForEmptyTreeSet_shouldCheckForEmpty() {
        Set<String> emptyTreeSet = new TreeSet<>();
        Assert.assertTrue(emptyTreeSet.isEmpty());
    }

    @Test
    public void whenIteratingTreeSet_shouldIterateTreeSetInAscendingOrder() {
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("First");
        treeSet.add("Second");
        treeSet.add("Third");
        Iterator<String> itr = treeSet.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    @Test
    public void whenIteratingTreeSet_shouldIterateTreeSetInDescendingOrder() {
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("First");
        treeSet.add("Second");
        treeSet.add("Third");
        Iterator<String> itr = treeSet.descendingIterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenModifyingTreeSetWhileIterating_shouldThrowException() {
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("First");
        treeSet.add("Second");
        treeSet.add("Third");
        Iterator<String> itr = treeSet.iterator();
        while (itr.hasNext()) {
            itr.next();
            treeSet.remove("Second");
        }
    }

    @Test
    public void whenRemovingElementUsingIterator_shouldRemoveElement() {
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("First");
        treeSet.add("Second");
        treeSet.add("Third");
        Iterator<String> itr = treeSet.iterator();
        while (itr.hasNext()) {
            String element = itr.next();
            if (element.equals("Second"))
                itr.remove();
        }
        Assert.assertEquals(2, treeSet.size());
    }
}
