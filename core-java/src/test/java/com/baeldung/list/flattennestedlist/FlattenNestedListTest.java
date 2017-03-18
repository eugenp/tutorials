package com.baeldung.list.flattennestedlist;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FlattenNestedListTest {

    @Test
    public void givenListOfListOfString_flattenNestedList1() {
        // given
        List<String> ls1 = Arrays.asList("one:one", "one:two", "one:three");
        List<String> ls2 = Arrays.asList("two:one", "two:two", "two:three");
        List<String> ls3 = Arrays.asList("three:one", "three:two", "three:three");

        List<List<String>> list = Arrays.asList(ls1, ls2, ls3);

        // when
        List<String> ls = flattenListOfListsImperatively(list);

        // then
        assertNotNull(ls);
        assertTrue(ls.size() == 9);
        //TODO content assertion
    }

    @Test
    public void givenListOfListOfString_flattenNestedList2() {
        // given
        List<String> ls1 = Arrays.asList("one:one", "one:two", "one:three");
        List<String> ls2 = Arrays.asList("two:one", "two:two", "two:three");
        List<String> ls3 = Arrays.asList("three:one", "three:two", "three:three");

        List<List<String>> list = Arrays.asList(ls1, ls2, ls3);

        // when
        List<String> ls = flattenListOfListsStream(list);

        // then
        assertNotNull(ls);
        assertTrue(ls.size() == 9);
        //TODO content assertion
    }

    public <T> List<T> flattenListOfListsImperatively(List<List<T>> list) {
        List<T> ls = new ArrayList<>();
        list.forEach(ls::addAll);
        return ls;
    }

    public <T> List<T> flattenListOfListsStream(List<List<T>> list) {
        return list.stream()
          .flatMap(Collection::stream)
          .collect(Collectors.toList());
    }

}
