package com.baeldung.list.listoflist;

import com.baeldung.java.list.Flower;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

public class AddElementsToListUnitTest {

    List<Flower> flowers;

    @Before
    public void init() {
        this.flowers = new ArrayList<>(Arrays.asList(
                new Flower("Poppy", 12),
                new Flower("Anemone", 8),
                new Flower("Catmint", 12)));
    }
    @Test
    public void givenAList_whenTargetListIsEmpty_thenReturnTargetListWithNewItems() {
        List<Flower> anotherList = new ArrayList<>();
        anotherList.addAll(flowers);
        assertEquals(anotherList.size(), flowers.size());
        Assert.assertTrue(anotherList.containsAll(flowers));
    }
    @Test
    public void givenAList_whenTargetListIsEmpty_thenReturnTargetListWithOneModifiedElementByConstructor() {
        List<Flower> anotherList = new ArrayList<>();
        anotherList.addAll(flowers);
        Flower flower = anotherList.get(0);
        flower.setPetals(flowers.get(0).getPetals() * 3);
        assertEquals(anotherList.size(), flowers.size());
        Assert.assertTrue(anotherList.containsAll(flowers));
    }
    @Test
    public void givenAListAndElements_whenUseCollectionsAddAll_thenAddElementsToTargetList() {
        List<Flower> target = new ArrayList<>();
        Collections.addAll(target, flowers.get(0), flowers.get(1), flowers.get(2), flowers.get(0));
        assertEquals(target.size(), 4);
    }
    @Test
    public void givenTwoList_whenSourceListDoesNotHaveNullElements_thenAddElementsToTargetListSkipFirstElementByStreamProcess() {
        List<Flower> flowerVase = new ArrayList<>();
        flowers.stream()
                .skip(1)
                .forEachOrdered(flowerVase::add);
        assertEquals(flowerVase.size() + 1, flowers.size());
        assertFalse(flowerVase.containsAll(flowers));
    }
    @Test
    public void givenTwoList_whenSourceListDoesNotHaveNullElements_thenAddElementsToTargetListFilteringElementsByStreamProcess() {
        List<Flower> flowerVase = new ArrayList<>();
        flowers.stream()
                .filter(f -> f.getPetals() > 10)
                .forEachOrdered(flowerVase::add);
        assertEquals(flowerVase.size() + 1, flowers.size());
        assertFalse(flowerVase.containsAll(flowers));
    }
    @Test
    public void givenAList_whenListIsNotNull_thenAddElementsToListByStreamProcessWihtOptional() {
        List<Flower> target = new ArrayList<>();
        Optional.ofNullable(flowers)
                .ifPresent(target::addAll);
        assertNotNull(target);
        assertEquals(target.size(), 3);
    }
}
