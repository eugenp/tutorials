package com.baeldung.java.listInitialization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.java.Log;

import org.junit.Assert;
import org.junit.Test;

@Log
public class ListInitializationUnitTest {

    @Test
    public void givenAnonymousInnerClass_thenInitialiseList() {
        List<String> cities = new ArrayList() {
            {
                add("New York");
                add("Rio");
                add("Tokyo");
            }
        };

        Assert.assertTrue(cities.contains("New York"));
    }

    @Test
    public void givenArraysAsList_thenInitialiseList() {
        List<String> list = Arrays.asList("foo", "bar");

        Assert.assertTrue(list.contains("foo"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenArraysAsList_whenAdd_thenUnsupportedException() {
        List<String> list = Arrays.asList("foo", "bar");

        list.add("baz");
    }

    @Test
    public void givenArrayAsList_whenCreated_thenShareReference() {
        String[] array = { "foo", "bar" };
        List<String> list = Arrays.asList(array);
        array[0] = "baz";
        Assert.assertEquals("baz", list.get(0));
    }

    @Test
    public void givenStream_thenInitializeList() {
        List<String> list = Stream.of("foo", "bar")
            .collect(Collectors.toList());

        Assert.assertTrue(list.contains("foo"));
    }
}
