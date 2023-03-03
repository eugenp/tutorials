package com.baeldung.listduplicate;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ListDuplicateUnitTest {
    private static ListDuplicate listDuplicate;

    @BeforeClass
    public static void setup() {
        listDuplicate = new ListDuplicate();
    }

    @Test
    public void givenList_whenUsingSet_thenReturnDuplicateElements() {
        List<Integer> list = Arrays.asList(1, 2, 3, 3, 4, 4, 5);
        List<Integer> duplicates = listDuplicate.listDuplicateUsingSet(list);
        Assert.assertEquals(duplicates.size(), 2);
        Assert.assertEquals(duplicates.contains(3), true);
        Assert.assertEquals(duplicates.contains(4), true);
        Assert.assertEquals(duplicates.contains(1), false);
    }

    @Test
    public void givenList_whenUsingFrequencyMap_thenReturnDuplicateElements() {
        List<Integer> list = Arrays.asList(1, 2, 3, 3, 4, 4, 5);
        List<Integer> duplicates = listDuplicate.listDuplicateUsingMap(list);
        Assert.assertEquals(duplicates.size(), 2);
        Assert.assertEquals(duplicates.contains(3), true);
        Assert.assertEquals(duplicates.contains(4), true);
        Assert.assertEquals(duplicates.contains(1), false);
    }

    @Test
    public void givenList_whenUsingFilterAndSetAdd_thenReturnDuplicateElements() {
        List<Integer> list = Arrays.asList(1, 2, 3, 3, 4, 4, 5);
        List<Integer> duplicates = listDuplicate.listDuplicateUsingFilterAndSetAdd(list);
        Assert.assertEquals(duplicates.size(), 2);
        Assert.assertEquals(duplicates.contains(3), true);
        Assert.assertEquals(duplicates.contains(4), true);
        Assert.assertEquals(duplicates.contains(1), false);
    }

    @Test
    public void givenList_whenUsingCollectionsFrequency_thenReturnDuplicateElements() {
        List<Integer> list = Arrays.asList(1, 2, 3, 3, 4, 4, 5);
        List<Integer> duplicates = listDuplicate.listDuplicateUsingCollectionsFrequency(list);
        Assert.assertEquals(duplicates.size(), 2);
        Assert.assertEquals(duplicates.contains(3), true);
        Assert.assertEquals(duplicates.contains(4), true);
        Assert.assertEquals(duplicates.contains(1), false);
    }

    @Test
    public void givenList_whenUsingMapAndCollectorsGroupingBy_thenReturnDuplicateElements() {
        List<Integer> list = Arrays.asList(1, 2, 3, 3, 4, 4, 5);
        List<Integer> duplicates = listDuplicate.listDuplicateUsingCollectionsFrequency(list);
        Assert.assertEquals(duplicates.size(), 2);
        Assert.assertEquals(duplicates.contains(3), true);
        Assert.assertEquals(duplicates.contains(4), true);
        Assert.assertEquals(duplicates.contains(1), false);
    }

}
