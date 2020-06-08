package com.baeldung.arraystostring;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JavaArraysToStringUnitTest {

    @Test
    public void givenInstanceOfArray_whenTryingToConvertToString_thenNameOfClassIsShown() {
        Object[] arrayOfObjects = { "John", 2, true };
        assertTrue(arrayOfObjects.toString().startsWith("[Ljava.lang.Object;"));
    }

    @Test
    public void givenInstanceOfArray_useArraysToStringToConvert_thenValueOfObjectsAreShown() {
        Object[] arrayOfObjects = { "John", 2, true };
        assertEquals(Arrays.toString(arrayOfObjects), "[John, 2, true]");
    }

    @Test
    public void givenInstanceOfDeepArray_userArraysDeepToStringToConvert_thenValueOfInnerObjectsAreShown() {
        Object[] innerArray = { "We", "Are", "Inside" };
        Object[] arrayOfObjects = { "John", 2, innerArray };
        assertEquals(Arrays.deepToString(arrayOfObjects), "[John, 2, [We, Are, Inside]]");
    }

    @Test
    public void givenInstanceOfDeepArray_useStreamsToConvert_thenValueOfObjectsAreShown() {
        Object[] arrayOfObjects = { "John", 2, true };
        List<String> listOfString = Stream.of(arrayOfObjects)
            .map(Object::toString)
            .collect(Collectors.toList());
        assertEquals(listOfString.toString(), "[John, 2, true]");
    }
}
