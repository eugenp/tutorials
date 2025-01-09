package com.baeldung.listregexfilter;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RegexFilterExampleUnitTest {

    RegexFilterExample regexFilterExample = new RegexFilterExample();

    @Test
    void whenfilterUsingPatternAndPredicateCalled_thenReturnElementsStartingWithA() {
        List<String> newList = regexFilterExample.filterUsingPatternAndPredicate();
        Assertions.assertEquals(List.of("apple", "apricot", "avocado"), newList);
    }

    @Test
    void whenfilterUsingStringMatchesCalled_thenReturnElementsContainingDig() {
        List<String> newList = regexFilterExample.filterUsingStringMatches();
        Assertions.assertEquals(List.of("123", "789"), newList);
    }

    @Test
    void whenfilterUsingPatternCompileCalled_thenReturnElementsStartingWithT3() {
        List<String> newList = regexFilterExample.filterUsingPatternCompile();
        Assertions.assertEquals(List.of("two", "three"), newList);
    }

    @Test
    void whenfilterUsingCollectorsPartitioningByCalled_thenReturnElementsStartingWithA4() {
        Map<Boolean, List<String>> newList = regexFilterExample.filterUsingCollectorsPartitioningBy();
        Assertions.assertEquals(List.of("apple", "apricot"), newList.get(true));
        Assertions.assertEquals(List.of("banana", "berry"), newList.get(false));
    }
}
