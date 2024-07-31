package com.baeldung.java10;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

public class Java10FeaturesUnitTest {

    private List<Integer> someIntList;
    
    @Before
    public void setup() {
        someIntList = new ArrayList<>();
        
        someIntList.add(1);
        someIntList.add(2);
        someIntList.add(3);
    }

    @Test
    public void whenVarInitWithString_thenGetStringTypeVar() {
        var message = "Hello, Java 10";
        assertTrue(message instanceof String);
    }

    @Test
    public void whenVarInitWithAnonymous_thenGetAnonymousType() {
        var obj = new Object() {};
        assertFalse(obj.getClass().equals(Object.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenModifyCopyOfList_thenThrowsException() {
        List<Integer> copyList = List.copyOf(someIntList);
        copyList.add(4);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenModifyToUnmodifiableList_thenThrowsException() {
        List<Integer> evenList = someIntList.stream()
          .filter(i -> i % 2 == 0)
          .collect(Collectors.toUnmodifiableList());
        evenList.add(4);
    }

    @Test
    public void whenListContainsInteger_OrElseThrowReturnsInteger() {
        Integer firstEven = someIntList.stream()
          .filter(i -> i % 2 == 0)
          .findFirst()
          .orElseThrow();
        is(firstEven).equals(Integer.valueOf(2));
    }
}
