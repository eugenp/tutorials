package com.baeldung.convertToMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class ConvertToMapUnitTest {

    private List<Book> bookList;
    private ConvertToMap convertToMap = new ConvertToMap();

    @Before
    public void init() {
        bookList = new ArrayList<>();
        bookList.add(new Book("The Fellowship of the Ring", 1954, "0395489318"));
        bookList.add(new Book("The Two Towers", 1954, "0345339711"));
        bookList.add(new Book("The Return of the King", 1955, "0618129111"));
    }

    @Test
    public void whenConvertFromListToMap() {
        assertTrue(convertToMap.listToMap(bookList).size() == 3);
    }

    @Test(expected = IllegalStateException.class)
    public void whenMapHasDuplicateKey_without_merge_function_then_runtime_exception() {
        convertToMap.listToMapWithDupKeyError(bookList);
    }

    @Test
    public void whenMapHasDuplicateKey_with_merge_function() {
        assertTrue(convertToMap.listToMapWithDupKey(bookList).size() == 2);
    }

    @Test
    public void whenCreateConcurrentHashMap() {
        assertTrue(convertToMap.listToConcurrentMap(bookList) instanceof ConcurrentHashMap);
    }

    @Test
    public void whenMapisSorted() {
        assertTrue(convertToMap.listToSortedMap(bookList).firstKey().equals("The Fellowship of the Ring"));
    }
}
