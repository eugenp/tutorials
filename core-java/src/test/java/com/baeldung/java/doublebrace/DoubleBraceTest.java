package com.baeldung.java.doublebrace;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class DoubleBraceTest {

    @Test
    public void whenInitializeSetWithoutDoubleBraces_containsElements() {
        final Set<String> countries = new HashSet<String>();
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
        final Set<String> countries = Collections.unmodifiableSet(Stream.of("India", "USSR", "USA").collect(toSet()));
        assertTrue(countries.contains("India"));
    }

}
