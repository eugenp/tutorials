package com.baeldung.java.set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;
  
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class HashSetInitalizingUnitTest {
    @Test
    public void whenUsingJava_usingArraysStaticMethod_thenCorrectSize() {
        Set<String> set = new HashSet<>(Arrays.asList("a", "b", "c"));
        assertEquals(3, set.size());
    }

    @Test
    public void whenUsingJava_usingAnonymousClass_thenCorrectSize() {
        Set<String> set = new HashSet<String>(){{
            add("a");
            add("b");
            add("c");
        }};
        assertEquals(3, set.size());
    }

    @Test
    public void whenUsingJava_creatingSingletonSet_thenCorrectSize() {
        Set<String> set = Collections.singleton("a");
        assertEquals(1, set.size());
    }

    public static final <T> Set<T> newHashSet(T... objs) {
        Set<T> set = new HashSet<T>();
        Collections.addAll(set, objs);
        return set;
    }

    @Test
    public void whenUsingJava_usingCustomStaticUtilMethod_thenCorrectSize() {
        Set<String> set = newHashSet("a","b","c");
        assertEquals(3, set.size());
    }

    @Test
    public void whenUsingJava8_usingCollectOnStream_thenCorrectSize() {
        Set<String> set = Stream.of("a", "b", "c").collect(Collectors.toCollection(HashSet::new));
        assertEquals(3, set.size());
    }

    // Requires Java9 - uncomment if you are using Java 9 or higher
    /*@Test
    public void whenUsingJava9_usingCollectOnStream_thenCorrectSize() {
        Set set = Set.of("a", "b", "c");
        assertEquals(3, set.size());
    }*/

    @Test
    public void whenUsingGoogleGuava_createMutableSet_thenCorrectSize() {
        Set<String> set = Sets.newHashSet("a", "b", "c");
        assertEquals(3, set.size());
    }

    @Test
    public void whenUsingGoogleGuava_createImmutableSet_thenCorrectSize() {
        Set<String> set = ImmutableSet.of("a", "b", "c");
        assertEquals(3, set.size());
    }
}
