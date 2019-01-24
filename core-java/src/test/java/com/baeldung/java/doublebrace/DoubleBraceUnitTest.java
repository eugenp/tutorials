package com.baeldung.java.doublebrace;

import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * 测试：双支撑
 */
public class DoubleBraceUnitTest {

    @Test
    public void whenInitializeSetWithoutDoubleBraces_containsElements() {
        final Set<String> countries = new HashSet<>();
        countries.add("India");
        countries.add("USSR");
        countries.add("USA");
        assertTrue(countries.contains("India"));
    }

    /**
     * 初始化Set<String>并赋值
     */
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
         Set<String> countries = Stream.<String>of("India", "USSR", "USA")
           .collect(Collectors.collectingAndThen(Collectors.toSet(), new Function<Set<String>, Set<String>>() {
               @Override
               public Set<String> apply(Set<String> s) {
                   return Collections.unmodifiableSet(s);
               }
           }));

        assertTrue(countries.contains("India"));
    }

}
