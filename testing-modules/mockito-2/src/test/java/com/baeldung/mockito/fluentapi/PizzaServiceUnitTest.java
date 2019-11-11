package com.baeldung.mockito.fluentapi;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class PizzaServiceUnitTest {

    @Test
    public void test() {
        
    }

    public List<String> convertAllToUpperCase(List<String> words) {

        return words.stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList());

    }

}
