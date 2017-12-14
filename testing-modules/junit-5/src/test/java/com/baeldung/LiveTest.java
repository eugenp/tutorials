package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

public class LiveTest {

    private List<String> in = new ArrayList<>(Arrays.asList("Hello", "Yes", "No"));
    private List<String> out = new ArrayList<>(Arrays.asList("Cześć", "Tak", "Nie"));

    @TestFactory
    public Stream<DynamicTest> translateDynamicTestsFromStream() {

        return in.stream()
            .map(word -> DynamicTest.dynamicTest("Test translate " + word, () -> {
                int id = in.indexOf(word);
                assertEquals(out.get(id), translate(word));
            }));
    }

    private String translate(String word) {
        if ("Hello".equalsIgnoreCase(word)) {
            return "Cześć";
        } else if ("Yes".equalsIgnoreCase(word)) {
            return "Tak";
        } else if ("No".equalsIgnoreCase(word)) {
            return "Nie";
        }
        return "Error";
    }

}
