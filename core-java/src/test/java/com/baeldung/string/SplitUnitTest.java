package com.baeldung.string;

import com.google.common.base.Splitter;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class SplitUnitTest {

    @Test
    public void givenString_whenSplit_thenRetrunsArray_through_JavaLangString() {
        assertArrayEquals("split by comma", Arrays.asList("peter", "james", "thomas").toArray(), "peter,james,thomas".split(","));
        assertArrayEquals("split by whitespace", Arrays.asList("car", "jeep", "scooter").toArray(), "car jeep scooter".split(" "));
        assertArrayEquals("split by hyphen", Arrays.asList("1", "120", "232323").toArray(), "1-120-232323".split("-"));
        assertArrayEquals("split by dot", Arrays.asList("192", "168", "1", "178").toArray(), "192.168.1.178".split("\\."));
        assertArrayEquals("split by a regex", Arrays.asList("b", "a", "e", "l", "d", "u", "n", "g").toArray(),
                          "b a, e, l.d u, n g".split("\\s+|,\\s*|\\.\\s*"));
    }

    @Test
    public void givenString_whenSplit_thenRetrunsArray_through_StringUtils() {
        StringUtils.split("car jeep scooter");

        assertArrayEquals("split by whitespace", Arrays.asList("car", "jeep", "scooter").toArray(), StringUtils.split("car jeep scooter"));
        assertArrayEquals("split by space, extra spaces ignored", Arrays.asList("car", "jeep", "scooter").toArray(),
                          StringUtils.split("car    jeep   scooter"));
        assertArrayEquals("split by colon", Arrays.asList("car", "jeep", "scooter").toArray(), StringUtils.split("car:jeep:scooter", ":"));
        assertArrayEquals("split by dot", Arrays.asList("car", "jeep", "scooter").toArray(), StringUtils.split("car.jeep.scooter", "."));
    }

    @Test
    public void givenString_whenSplit_thenReturnsIterable_Splitter() {
        //given
        Iterable<String> result = Splitter.on(',').trimResults().omitEmptyStrings().split("car,jeep,,   scooter");
        List<String> resultList = StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());

        assertEquals(Arrays.asList("car", "jeep", "scooter") , resultList);
    }
}
