package com.baeldung.lombok.extensionmethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.experimental.ExtensionMethod;

@ExtensionMethod(ListUtils.class)
public class ListUtilsUnitTest {

    @Test
    public void givenIntegerList_whenUsingExtensionMethod_thenSum() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int total = numbers.sum();
        assertEquals(15, total, "The sum of the list should be 15");
    }

    @Test
    public void givenDoubleList_whenUsingExtensionMethod_thenSum() {
        List<Double> numbers = Arrays.asList(1.0, 2.0, 3.0);
        int total = numbers.sum();
        assertEquals(6, total, "The sum of the list should be 6");
    }
}

