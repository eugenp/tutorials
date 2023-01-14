package com.baeldung.listduplicate;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class ListDuplicateUnitTest {
    private static ListDuplicate listDuplicate;
    private static List<Integer> list;

    @BeforeClass
    public static void setup_tests() {
        listDuplicate = new ListDuplicate();
        list = Arrays.asList(1, 2, 3, 3, 4, 4, 5);
    }
    @Test
    public void givenList_whenUsingSet_thenReturnDuplicateElements() {
        List<Integer> duplicates = listDuplicate.listDuplicateUsingSet(list);
        basicExpectations(duplicates);
    }

    @Test
    public void givenList_whenUsingFrequencyMap_thenReturnDuplicateElements() {
        List<Integer> duplicates = listDuplicate.listDuplicateUsingMap(list);
        basicExpectations(duplicates);
    }

    @Test
    public void givenList_whenUsingFilterAndSetAdd_thenReturnDuplicateElements() {
        List<Integer> duplicates = listDuplicate.listDuplicateUsingFilterAndSetAdd(list);
        basicExpectations(duplicates);
    }

    @Test
    public void givenList_whenUsingCollectionsFrequency_thenReturnDuplicateElements() {
        List<Integer> duplicates = listDuplicate.listDuplicateUsingCollectionsFrequency(list);
        basicExpectations(duplicates);
    }

    @Test
    public void givenList_whenUsingMapAndCollectorsGroupingBy_thenReturnDuplicateElements() {
        List<Integer> duplicates = listDuplicate.listDuplicateUsingCollectionsFrequency(list);
        basicExpectations(duplicates);
    }

    private void basicExpectations(List<Integer> duplicates) {
        Assert.assertEquals(duplicates.size(), 2);
        Assert.assertEquals(duplicates.contains(3), true);
        Assert.assertEquals(duplicates.contains(4), true);
        Assert.assertEquals(duplicates.contains(1), false);
    }
}
