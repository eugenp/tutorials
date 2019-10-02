package com.baeldung.array;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class AddElementToEndOfArrayUnitTest {

    AddElementToEndOfArray addElementToEndOfArray;

    @Before
    public void init() {
        addElementToEndOfArray = new AddElementToEndOfArray();
    }

    @Test
    public void givenSourceArrayAndElement_whenAddElementUsingArraysCopyIsInvoked_thenNewElementMustBeAdded() {
        Integer[] sourceArray = {1, 2, 3, 4};
        int elementToAdd = 5;

        Integer[] destArray = addElementToEndOfArray.addElementUsingArraysCopyOf(sourceArray, elementToAdd);

        Integer[] expectedArray = {1, 2, 3, 4, 5};
        assertArrayEquals(expectedArray, destArray);
    }

    @Test
    public void givenSourceArrayAndElement_whenAddElementUsingArrayListIsInvoked_thenNewElementMustBeAdded() {
        Integer[] sourceArray = {1, 2, 3, 4};
        int elementToAdd = 5;

        Integer[] destArray = addElementToEndOfArray.addElementUsingArrayList(sourceArray, elementToAdd);

        Integer[] expectedArray = {1, 2, 3, 4, 5};
        assertArrayEquals(expectedArray, destArray);
    }

    @Test
    public void givenSourceArrayAndElement_whenAddElementUsingSystemArrayCopyIsInvoked_thenNewElementMustBeAdded() {
        Integer[] sourceArray = {1, 2, 3, 4};
        int elementToAdd = 5;

        Integer[] destArray = addElementToEndOfArray.addElementUsingSystemArrayCopy(sourceArray, elementToAdd);

        Integer[] expectedArray = {1, 2, 3, 4, 5};
        assertArrayEquals(expectedArray, destArray);
    }
}
