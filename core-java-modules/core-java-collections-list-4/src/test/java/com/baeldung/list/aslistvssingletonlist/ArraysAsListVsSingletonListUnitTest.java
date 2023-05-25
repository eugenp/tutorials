package com.baeldung.list.aslistvssingletonlist;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class ArraysAsListVsSingletonListUnitTest {

    @Test
    void givenListFromArraysAsList_whenChangingStructureAndElement_thenGetExpectedResult() {
        List<String> arraysAsList = Arrays.asList("ONE");
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(
            () -> arraysAsList.add("TWO")
        );

        arraysAsList.set(0, "A brand new string");
        assertThat(arraysAsList.get(0)).isEqualTo("A brand new string");
    }

    @Test
    void givenSingletonList_whenChangingStructureAndElement_thenThrowException() {
        List<String> singletonList = Collections.singletonList("ONE");
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(
            () -> singletonList.add("TWO")
        );
        assertThatExceptionOfType(UnsupportedOperationException.class).isThrownBy(
            () -> singletonList.set(0, "A brand new string")
        );
    }

    @Test
    void givenAnArray_whengetListByArraysAsList_thenTheListIsBackedByTheArray() {
        String[] theArray = new String[] { "ONE", "TWO" };
        List<String> theList = Arrays.asList(theArray);
        //changing the list, the array is changed too
        theList.set(0, "ONE [changed in list]");
        assertThat(theArray[0]).isEqualTo("ONE [changed in list]");

        //changing the array, the list is changed too
        theArray[1] = "TWO [changed in array]";
        assertThat(theList.get(1)).isEqualTo("TWO [changed in array]");
    }
}