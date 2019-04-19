package com.baeldung.array;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddElementToEndOfArrayUnitTest {

    AddElementToEndOfArray addElementToEndOfArray;

    @Before
    public void init(){
        addElementToEndOfArray = new AddElementToEndOfArray();
    }

    @Test
    public void givenSourceArrayAndElement_whenAddElementUsingArraysCopyIsInvoked_thenNewElementMustBeAdded(){
        Integer[] sourceArray = {1,2,3,4};
        int elementToAdd = 5;

        Integer[] destArray = addElementToEndOfArray.addElementUsingArraysCopyOf(sourceArray, elementToAdd);

        assertEquals(elementToAdd, destArray[destArray.length-1].intValue());
    }

    @Test
    public void givenSourceArrayAndElement_whenAddElementUsingArrayListIsInvoked_thenNewElementMustBeAdded(){
        Integer[] sourceArray = {1,2,3,4};
        int elementToAdd = 5;

        Integer[] destArray = addElementToEndOfArray.addElementUsingArrayList(sourceArray, elementToAdd);

        assertEquals(elementToAdd, destArray[destArray.length-1].intValue());
    }

    @Test
    public void givenSourceArrayAndElement_whenAddElementUsingSystemArrayCopyIsInvoked_thenNewElementMustBeAdded(){
        Integer[] sourceArray = {1,2,3,4};
        int elementToAdd = 5;

        Integer[] destArray = addElementToEndOfArray.addElementUsingSystemArrayCopy(sourceArray, elementToAdd);

        assertEquals(elementToAdd, destArray[destArray.length-1].intValue());
    }
}
