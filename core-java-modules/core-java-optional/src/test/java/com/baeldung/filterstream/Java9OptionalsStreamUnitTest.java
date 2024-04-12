package com.baeldung.filterstream;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class Java9OptionalsStreamUnitTest {

    private static List<Optional<String>> listOfOptionals = Arrays.asList(Optional.empty(), Optional.of("foo"), Optional.empty(), Optional.of("bar"));

    @Test
    public void filterOutPresentOptionalsWithFilter() {
        assertEquals(4, listOfOptionals.size());

        List<String> filteredList = listOfOptionals.stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());

        assertEquals(2, filteredList.size());
        assertEquals("foo", filteredList.get(0));
        assertEquals("bar", filteredList.get(1));
    }

    @Test
    public void filterOutPresentOptionalsWithFlatMap() {
        assertEquals(4, listOfOptionals.size());

        List<String> filteredList = listOfOptionals.stream().flatMap(o -> o.isPresent() ? Stream.of(o.get()) : Stream.empty()).collect(Collectors.toList());
        assertEquals(2, filteredList.size());

        assertEquals("foo", filteredList.get(0));
        assertEquals("bar", filteredList.get(1));
    }

    @Test
    public void filterOutPresentOptionalsWithFlatMap2() {
        assertEquals(4, listOfOptionals.size());

        List<String> filteredList = listOfOptionals.stream().flatMap(o -> o.map(Stream::of).orElseGet(Stream::empty)).collect(Collectors.toList());
        assertEquals(2, filteredList.size());

        assertEquals("foo", filteredList.get(0));
        assertEquals("bar", filteredList.get(1));
    }


//    Uncomment code when code base is compatible with Java 9
//    @Test
//    public void filterOutPresentOptionalsWithJava9() {
//        assertEquals(4, listOfOptionals.size());
//
//        List<String> filteredList = listOfOptionals.stream().flatMap(Optional::stream).collect(Collectors.toList());
//
//        assertEquals(2, filteredList.size());
//        assertEquals("foo", filteredList.get(0));
//        assertEquals("bar", filteredList.get(1));
//    }

}
