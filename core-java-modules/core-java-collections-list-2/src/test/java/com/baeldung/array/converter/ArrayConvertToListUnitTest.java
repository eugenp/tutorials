package com.baeldung.array.converter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ArrayConvertToListUnitTest {

    @Test
    public void givenAnStringArray_whenConvertArrayToList_thenListCreated() {
        String[] flowers = { "Ageratum", "Allium", "Poppy", "Catmint" };
        List<String> flowerList = Arrays.asList(flowers);

        assertNotNull(flowerList);
        assertEquals(flowerList.size(), 4);
        assertEquals(flowerList.get(0), "Ageratum");
        assertEquals(flowerList.get(1), "Allium");
        assertEquals(flowerList.get(2), "Poppy");
        assertEquals(flowerList.get(3), "Catmint");
    }

    @Test
    public void givenAnIntArray_whenConvertArrayToList_thenListWithOneElementCreated() {
        int[] primitives = { 1, 2, 3, 4 };
        List numbers = Arrays.asList(primitives);

        assertNotNull(numbers);
        assertEquals(numbers.size(), 1);
        assertEquals(numbers.get(0), primitives);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenAnStringArray_whenConvertArrayToListAndAddAnElement_thenThrowUnsupportedOperationException() {
        String[] flowers = { "Ageratum", "Allium", "Poppy", "Catmint" };
        List<String> flowerList = Arrays.asList(flowers);

        assertNotNull(flowerList);
        assertEquals(flowerList.size(), 4);
        assertEquals(flowerList.get(0), "Ageratum");
        assertEquals(flowerList.get(1), "Allium");
        assertEquals(flowerList.get(2), "Poppy");
        assertEquals(flowerList.get(3), "Catmint");

        flowerList.add("Celosia");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenAnStringArray_whenConvertArrayToListAndRemoveAnElement_thenThrowUnsupportedOperationException() {
        String[] flowers = { "Ageratum", "Allium", "Poppy", "Catmint" };
        List<String> flowerList = Arrays.asList(flowers);

        assertNotNull(flowerList);
        assertEquals(flowerList.size(), 4);
        assertEquals(flowerList.get(0), "Ageratum");
        assertEquals(flowerList.get(1), "Allium");
        assertEquals(flowerList.get(2), "Poppy");
        assertEquals(flowerList.get(3), "Catmint");

        flowerList.remove("Poppy");
    }

    @Test
    public void givenAnStringArray_whenCreateListFromArrayAndAddAnElement_thenListOk() {
        String[] flowers = { "Ageratum", "Allium", "Poppy", "Catmint" };
        List<String> flowerList = Arrays.asList(flowers);

        assertNotNull(flowerList);
        assertEquals(flowerList.size(), 4);

        assertEquals(flowerList.get(0), "Ageratum");
        assertEquals(flowerList.get(1), "Allium");
        assertEquals(flowerList.get(2), "Poppy");
        assertEquals(flowerList.get(3), "Catmint");

        List<String> newflowerList = new ArrayList<>(flowerList);

        assertNotNull(newflowerList);
        assertEquals(newflowerList.size(), 4);
        assertEquals(newflowerList.get(0), "Ageratum");
        assertEquals(newflowerList.get(1), "Allium");
        assertEquals(newflowerList.get(2), "Poppy");
        assertEquals(newflowerList.get(3), "Catmint");

        newflowerList.add("Celosia");

        assertEquals(newflowerList.size(), 5);
        assertEquals(newflowerList.get(4), "Celosia");
    }

    @Test
    public void givenAnStringArray_whenIterateArrayAndAddTheElementsToNewListAndAddAnElement_thenListOk() {
        String[] flowers = { "Ageratum", "Allium", "Poppy", "Catmint" };

        List<String> flowerList = new ArrayList<>();
        for(String flower: flowers) {
            flowerList.add(flower);
        }

        assertNotNull(flowerList);
        assertEquals(flowerList.size(), 4);

        assertEquals(flowerList.get(0), "Ageratum");
        assertEquals(flowerList.get(1), "Allium");
        assertEquals(flowerList.get(2), "Poppy");
        assertEquals(flowerList.get(3), "Catmint");

        flowerList.add("Celosia");

        assertEquals(flowerList.size(), 5);
        assertEquals(flowerList.get(4), "Celosia");
    }
}
