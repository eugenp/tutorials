package com.baeldung.passcollectiontovarargs;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassCollectionToVarargsUnitTest {

    private List<String> listOfStrings;

    @Test
    void givenList_whenUsingCollectionToArray_thenInvokeVarargsMethod() {
        method(listOfStrings.toArray(new String[0]));
    }

    @Test
    void givenList_whenUsingForLoopToPopulateArray_thenInvokeVarargsMethod() {
        String[] array = new String[listOfStrings.size()];
        for (int i = 0; i < listOfStrings.size(); i++) {
            array[i] = listOfStrings.get(i);
        }
        method(array);
    }

    @Test
    void givenList_whenUsingStreamAPI_thenInvokeVarargsMethod() {
        String[] array = listOfStrings.stream()
            .toArray(String[]::new);
        method(array);
    }

    @BeforeEach
    public void init() {
        listOfStrings = List.of("string one", "string two", "string three");
    }

    private void method(String... strings) {
    }
}