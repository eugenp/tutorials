package com.baeldung.lombok.extensionmethod;

import lombok.experimental.ExtensionMethod;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtensionMethod(ListUtils.class)
public class ListUtilsUnitTest {

    @Test
    public void givenList_whenUsingExtensionMethod_thenSum() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        int total = numbers.sum();
        assertEquals(10, total);
    }

    @Test
    public void givenEmptyList_whenUsingExtensionMethod_thenSum() {
        List<Integer> numbers = Collections.emptyList();
        int total = numbers.sum();
        assertEquals(0, total);
    }

    @Test
    public void givenMixedNumList_whenUsingExtensionMethod_thenSum() {
        List<Integer> numbers = Arrays.asList(-1, 2, -3, 4, -5);
        int total = numbers.sum();
        assertEquals(-3, total);
    }
}

