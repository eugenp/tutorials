package com.baeldung.collection;

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

public class WhenUsingTreeSet {

    private static class Element {
        private Integer id;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return id.toString();
        }
    }

    private Comparator<Element> comparator = (ele1, ele2) -> {
        return ele1.getId()
            .compareTo(ele2.getId());
    };

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

    @Test(expected = NullPointerException.class)
    public void whenAddingNullToNonEmptyTreeSet_shouldThrowException() {
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("First");
        treeSet.add(null);
    }

    @Test
    public void whenCheckingFirstElement_shouldReturnFirstElement() {
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("First");
        Assert.assertEquals("First", treeSet.first());
    }

    @Test
    public void whenCheckingLastElement_shouldReturnLastElement() {
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("First");
        treeSet.add("Last");
        Assert.assertEquals("Last", treeSet.last());
    }

    @Test
    public void whenUsingComparator_shouldSortAndInsertElements() {
        Set<Element> treeSet = new TreeSet<>(comparator);
        Element ele1 = new Element();
        ele1.setId(100);
        Element ele2 = new Element();
        ele2.setId(200);

        treeSet.add(ele1);
        treeSet.add(ele2);

        System.out.println(treeSet);
    }

    @Test
    public void whenUsingHeadSet_shouldReturnElementsLessThanSpecifiedElement() {
        Set<Element> treeSet = new TreeSet<>(comparator);
        Element ele1 = new Element();
        ele1.setId(100);
        Element ele2 = new Element();
        ele2.setId(200);

        treeSet.add(ele1);
        treeSet.add(ele2);

        System.out.println(treeSet);
    }

    @Test
    public void whenUsingSubSet_shouldReturnSubSetElements() {
        SortedSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(1);
        treeSet.add(2);
        treeSet.add(3);
        treeSet.add(4);
        treeSet.add(5);
        treeSet.add(6);

        Set<Integer> expectedSet = new TreeSet<>();
        expectedSet.add(2);
        expectedSet.add(3);
        expectedSet.add(4);
        expectedSet.add(5);

        Set<Integer> subSet = treeSet.subSet(2, 6);
        Assert.assertEquals(expectedSet, subSet);
    }

    @Test
    public void whenUsingHeadSet_shouldReturnHeadSetElements() {
        SortedSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(1);
        treeSet.add(2);
        treeSet.add(3);
        treeSet.add(4);
        treeSet.add(5);
        treeSet.add(6);

        Set<Integer> subSet = treeSet.headSet(6);
        Assert.assertEquals(subSet, treeSet.subSet(1, 6));
    }

    @Test
    public void whenUsingTailSet_shouldReturnTailSetElements() {
        NavigableSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(1);
        treeSet.add(2);
        treeSet.add(3);
        treeSet.add(4);
        treeSet.add(5);
        treeSet.add(6);

        Set<Integer> subSet = treeSet.tailSet(3);
        Assert.assertEquals(subSet, treeSet.subSet(3, true, 6, true));
    }
}
