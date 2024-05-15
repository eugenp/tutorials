package com.baeldung.indexawareset;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections4.set.ListOrderedSet;
import org.junit.Assert;
import org.junit.Test;

public class IndexOfSetElementsUsingListUnitTest {
    @Test
    public void givenHashSet_whenIndexOfElement_thenGivesIndex() {
        Set<Integer> set = new HashSet<>();
        set.add(100);
        set.add(20);
        set.add(300);
        set.add(0);
        set.add(-1);
        set.add(300);

        IndexOfElementsInSet<Integer> integerIndexOfElementsInSet = new IndexOfElementsInSet<>();
        int index100 = integerIndexOfElementsInSet.getIndexUsingIterator(set, 100);
        Assert.assertEquals(index100, integerIndexOfElementsInSet.getIndexUsingIterator(set, 100));

        Assert.assertEquals(-1, integerIndexOfElementsInSet.getIndexUsingIterator(set, 12));
    }

    @Test
    public void givenLinkedHashSet_whenIndexOfElement_thenGivesIndex() {
        Set<Integer> set = new LinkedHashSet<>();
        set.add(100);
        set.add(20);
        set.add(300);
        set.add(0);
        set.add(-1);
        set.add(300);

        IndexOfElementsInSet<Integer> integerIndexOfElementsInSet = new IndexOfElementsInSet<>();
        Assert.assertEquals(0, integerIndexOfElementsInSet.getIndexUsingIterator(set, 100));
    }

    @Test
    public void givenTreeSet_whenIndexOfElement_thenGivesIndex() {
        Set<Integer> set = new TreeSet<>();
        set.add(100);
        set.add(20);
        set.add(300);
        set.add(0);
        set.add(-1);
        set.add(300);

        IndexOfElementsInSet<Integer> integerIndexOfElementsInSet = new IndexOfElementsInSet<>();
        Assert.assertEquals(0, integerIndexOfElementsInSet.getIndexUsingIterator(set, -1));
        Assert.assertEquals(3, integerIndexOfElementsInSet.getIndexUsingIterator(set, 100));
    }

    @Test
    public void givenIndexAwareSet_whenIndexOfElement_thenGivesIndex() {
        InsertionIndexAwareSet<Integer> set = new InsertionIndexAwareSet<>();
        set.add(100);
        set.add(20);
        set.add(300);
        Assert.assertEquals(0, set.getIndexOf(100));
        Assert.assertEquals(2, set.getIndexOf(300));
        Assert.assertEquals(-1, set.getIndexOf(0));
    }

    @Test
    public void givenIndexAwareSetWithStrings_whenIndexOfElement_thenGivesIndex() {
        InsertionIndexAwareSet<String> set = new InsertionIndexAwareSet<>();
        set.add("Go");
        set.add("Java");
        set.add("Scala");
        set.add("Python");
        Assert.assertEquals(0, set.getIndexOf("Go"));
        Assert.assertEquals(2, set.getIndexOf("Scala"));
        Assert.assertEquals(-1, set.getIndexOf("C++"));
    }

    @Test
    public void givenListOrderedSet_whenIndexOfElement_thenGivesIndex() {
        ListOrderedSet<Integer> set = new ListOrderedSet<>();
        set.add(12);
        set.add(0);
        set.add(-1);
        set.add(50);

        Assert.assertEquals(0, set.indexOf(12));
        Assert.assertEquals(2, set.indexOf(-1));
        Assert.assertEquals(-1, set.indexOf(100));
    }

    @Test
    public void givenLinkedHashSet_whenIndexUsingUtilityMethod_thenReturnsIndex() {
        Set<Integer> set = new LinkedHashSet<>();
        set.add(100);
        set.add(20);
        set.add(300);
        set.add(0);
        set.add(-1);
        set.add(300);

        IndexOfElementsInSet<Integer> integerIndexOfElementsInSet = new IndexOfElementsInSet<>();
        Assert.assertEquals(-1, integerIndexOfElementsInSet.getIndexUsingForEach(set, 150));
    }
}
