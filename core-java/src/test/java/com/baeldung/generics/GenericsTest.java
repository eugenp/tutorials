package com.baeldung.generics;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

public class GenericsTest {

    // testing the generic method with Integer
    @Test
    public void givenArrayOfIntegers_thanListOfIntegersReturnedOK() {
        Integer[] intArray = { 1, 2, 3, 4, 5 };
        List<Integer> list = Generics.fromArrayToList(intArray);

        assertThat(list, hasItems(intArray));
    }

    // testing the generic method with String
    @Test
    public void givenArrayOfStrings_thanListOfStringsReturnedOK() {
        String[] stringArray = { "hello1", "hello2", "hello3", "hello4", "hello5" };
        List<String> list = Generics.fromArrayToList(stringArray);

        assertThat(list, hasItems(stringArray));
    }

    // testing the generic method with Number as upper bound with Integer
    // if we test fromArrayToListWithUpperBound with any type that doesn't
    // extend Number it will fail to compile
    @Test
    public void givenArrayOfIntegersAndNumberUpperBound_thanListOfIntegersReturnedOK() {
        Integer[] intArray = { 1, 2, 3, 4, 5 };
        List<Integer> list = Generics.fromArrayToListWithUpperBound(intArray);

        assertThat(list, hasItems(intArray));
    }

}
