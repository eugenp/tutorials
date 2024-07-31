package com.baeldung.core.doublebrace;

import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertTrue;

public class DoubleBraceUnitTest {

    @Test
    public void whenInitializeSetWithoutDoubleBraces_containsElements() {
        final Set<String> countries = new HashSet<>();
        countries.add("India");
        countries.add("USSR");
        countries.add("USA");
        assertTrue(countries.contains("India"));
    }

    @Test
    public void whenInitializeSetWithDoubleBraces_containsElements() {
        final Set<String> countries = new HashSet<String>() {

            {
                add("India");
                add("USSR");
                add("USA");
            }
        };
        assertTrue(countries.contains("India"));
    }

    @Test
    public void whenInitializeUnmodifiableSetWithDoubleBrace_containsElements() {
         Set<String> countries = Stream.of("India", "USSR", "USA")
           .collect(collectingAndThen(toSet(), Collections::unmodifiableSet));

        assertTrue(countries.contains("India"));
    }

}
