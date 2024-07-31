package com.baeldung.exception.arrayindexoutofbounds;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ArrayIndexOutOfBoundsExceptionDemoUnitTest {

    private static int[] numbers;

    @BeforeAll
    public static void setUp() {
        numbers = new int[] { 1, 2, 3, 4, 5 };
    }

    @Test
    void givenAnArrayOfSizeFive_whenAccessedElementBeyondRange_thenShouldThrowArrayIndexOutOfBoundsException() {
        assertThrows(ArrayIndexOutOfBoundsException.class,
          () -> ArrayIndexOutOfBoundsExceptionDemo.addArrayElementsUsingLoop(numbers));
    }

    @Test
    void givenAnArrayOfSizeFive_whenAccessedAnElementAtIndexEqualToSize_thenShouldThrowArrayIndexOutOfBoundsException() {
        assertThrows(ArrayIndexOutOfBoundsException.class,
          () -> ArrayIndexOutOfBoundsExceptionDemo.getArrayElementAtIndex(numbers, 5));
    }

    @Test
    void givenAListReturnedByArraysAsListMethod_whenAccessedAnElementAtIndexEqualToSize_thenShouldThrowArrayIndexOutOfBoundsException() {
        assertThrows(ArrayIndexOutOfBoundsException.class,
          () -> ArrayIndexOutOfBoundsExceptionDemo.getListElementAtIndex(5));
    }
}
