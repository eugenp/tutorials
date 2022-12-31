package com.baeldung.list.listandset;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class ListAndSetUnitTest {

    @Test
    public void givenList_whenDuplicates_thenAllowed(){
        List<Integer> integerList = new ArrayList<>();
        integerList.add(2);
        integerList.add(3);
        integerList.add(4);
        integerList.add(4);
        assertEquals(integerList.size(), 4);
    }

    @Test
    public void givenSet_whenDuplicates_thenNotAllowed(){
        Set<Integer> integerSet = new HashSet<>();
        integerSet.add(2);
        integerSet.add(3);
        integerSet.add(4);
        integerSet.add(4);
        assertEquals(integerSet.size(), 3);
    }

    @Test
    public void givenSet_whenOrdering_thenMayBeAllowed(){
        Set<Integer> set1 = new LinkedHashSet<>();
        set1.add(2);
        set1.add(3);
        set1.add(4);
        Set<Integer> set2 = new LinkedHashSet<>();
        set2.add(2);
        set2.add(3);
        set2.add(4);
        Assert.assertArrayEquals(set1.toArray(), set2.toArray());
    }
}
