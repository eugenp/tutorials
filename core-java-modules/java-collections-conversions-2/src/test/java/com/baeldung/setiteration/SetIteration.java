package com.baeldung.setiteration;

import com.google.common.collect.Sets;
import io.vavr.collection.Stream;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

class SetIteration {

    @Test
    void givenSet_whenIteratorUsed_shouldIterateOverElements() {
        // given
        Set<String> names = Sets.newHashSet("Tom", "Jane", "Karen");

        // when
        Iterator<String> namesIterator1 = names.iterator();
        Iterator<String> namesIterator2 = names.iterator();

        // then
        namesIterator1.forEachRemaining(System.out::println);
        while(namesIterator2.hasNext()) {
            System.out.println(namesIterator2.next());
        }
    }

    @Test
    void givenSet_whenStreamUsed_shouldIterateOverElements() {
        // given
        Set<String> names = Sets.newHashSet("Tom", "Jane", "Karen");

        // when & then
        String namesJoined = names.stream()
                .map(String::toUpperCase)
                .peek(System.out::println)
                .collect(Collectors.joining());
    }

    @Test
    void givenSet_whenEnhancedLoopUsed_shouldIterateOverElements() {
        // given
        Set<String> names = Sets.newHashSet("Tom", "Jane", "Karen");

        // when & then
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    void givenSet_whenMappedToArray_shouldIterateOverElements() {
        // given
        Set<String> names = Sets.newHashSet("Tom", "Jane", "Karen");

        // when & then
        Object[] namesArray = names.toArray();
        for (int i = 0; i < namesArray.length; i++) {
            System.out.println(i + ": " + namesArray[i]);
        }
    }

    @Test
    void givenSet_whenZippedWithIndex_shouldIterateOverElements() {
        // given
        Set<String> names = Sets.newHashSet("Tom", "Jane", "Karen");

        // when & then
        Stream.ofAll(names)
                .zipWithIndex()
                .forEach(t -> System.out.println(t._2() + ": " + t._1()));
    }
}
