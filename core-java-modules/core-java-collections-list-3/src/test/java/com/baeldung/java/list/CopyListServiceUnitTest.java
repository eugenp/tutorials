package com.baeldung.java.list;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CopyListServiceUnitTest {

    List<Flower> flowers;

    private CopyListService copyListService;

    @Before
    public void init() {
        this.copyListService = new CopyListService();
        this.flowers = new ArrayList<>();

        Flower poppy = new Flower("Poppy", 12);
        flowers.add(poppy);
        Flower anemone = new Flower("Anemone", 8);
        flowers.add(anemone);
        Flower catmint = new Flower("Catmint", 12);
        flowers.add(catmint);
        Flower diascia = new Flower("Diascia", 5);
        flowers.add(diascia);
        Flower iris = new Flower("Iris", 3);
        flowers.add(iris);
        Flower pansy = new Flower("Pansy", 5);
        flowers.add(pansy);
    }

    @Test
    public void givenAList_whenListDoesNotHaveNullElements_thenReturnAnotherListWithTheSameElementsByConstructor() {
        List<Flower> copy = copyListService.copyListByConstructor(flowers);
        assertEquals(copy.size(), flowers.size());
        assertTrue(copy.containsAll(flowers));
    }

    @Test
    public void givenAList_whenListDoesNotHaveNullElements_thenReturnAnotherListWithOneModifiedElementByConstructor() {
        List<Flower> copy = copyListService.copyListByConstructorAndEditOneFlowerInTheNewList(flowers);
        assertEquals(copy.size(), flowers.size());
        assertTrue(copy.containsAll(flowers));
    }

    @Test
    public void givenAList_whenListDoesNotHaveNullElements_thenReturnAnotherListWithTheSameElementsByAddAllmethod() {
        List<Flower> copy = copyListService.copyListByAddAllMethod(flowers);
        assertEquals(copy.size(), flowers.size());
        assertTrue(copy.containsAll(flowers));
    }

    @Test
    public void givenAList_whenListDoesNotHaveNullElements_thenReturnAnotherListWithOneModifiedElementByAddAllmethod() {
        List<Flower> copy = copyListService.copyListByAddAllMethodAndEditOneFlowerInTheNewList(flowers);
        assertEquals(copy.size(), flowers.size());
        assertTrue(copy.containsAll(flowers));
    }

    @Test
    public void givenAList_whenListsHaveSameSize_thenReturnAnotherListWithTheSameElementsByCopyMethod() {
        List<Integer> source = Arrays.asList(1,2,3);
        List<Integer> dest = Arrays.asList(4,5,6);

        dest = copyListService.copyListByCopyMethod(source, dest);
        assertEquals(dest.size(), source.size());
        assertTrue(dest.containsAll(source));
    }

    @Test
    public void givenAList_whenListsHaveDifferentSize_thenReturnAnotherListWithTheSameElementsByCopyMethod() {
        List<Integer> source = Arrays.asList(1,2,3);
        List<Integer> dest = Arrays.asList(5,6,7,8,9,10);

        dest = copyListService.copyListByCopyMethod(source, dest);
        assertNotEquals(dest.size(), source.size());
        assertTrue(dest.containsAll(source));
    }

    @Test
    public void givenAList_whenListDoesNotHaveNullElements_thenReturnAnotherListWithTheSameElementsByStreamProcess() {
        List<Flower> copy = copyListService.copyListByStream(flowers);
        assertEquals(copy.size(), flowers.size());
        assertTrue(copy.containsAll(flowers));
    }

    @Test
    public void givenAList_whenListDoesNotHaveNullElements_thenReturnAnotherListWithOneElementLessByStreamProcess() {
        List<Flower> copy = copyListService.copyListByStreamAndSkipFirstElement(flowers);
        assertNotEquals(copy.size(), flowers.size());
        assertEquals(copy.size() + 1, flowers.size());
        assertFalse(copy.containsAll(flowers));
    }

    @Test
    public void givenAList_whenListDoesNotHaveNullElements_thenReturnAnotherListWithFilterElementsByStreamProcess() {
        List<Flower> copy = copyListService.copyListByStreamWithFilter(flowers, 5);
        assertNotEquals(copy.size(), flowers.size());
        assertEquals(copy.size() + 3, flowers.size());
        assertFalse(copy.containsAll(flowers));
    }

    @Test
    public void givenAList_whenListIsNull_thenReturnEmptyListByStreamProcess() {
        List<Flower> copy = copyListService.copyListByStreamWithOptional(null);
        assertNotNull(copy);
        assertEquals(copy.size(), 0);
    }

    @Test
    public void givenAList_whenListIsNotNull_thenReturnAnotherListWithTheElementsByStreamProcess() {
        List<Flower> copy = copyListService.copyListByStreamWithOptional(flowers);
        assertEquals(copy.size(), flowers.size());
        assertTrue(copy.containsAll(flowers));
    }

    @Test
    public void givenAList_whenListIsNotNull_thenReturnAnotherListWithOneElementLessByStreamProcess() {
        List<Flower> copy = copyListService.copyListByStreamWithOptionalAndSkip(flowers);
        assertNotEquals(copy.size(), flowers.size());
        assertEquals(copy.size() + 1, flowers.size());
        assertFalse(copy.containsAll(flowers));
    }
}
