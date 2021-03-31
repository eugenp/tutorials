package com.baeldung.java10.streams;

import static java.util.stream.Collectors.toUnmodifiableList;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class StreamToImmutableJava10UnitTest {

    @Test
    public void whenUsingCollectorsToUnmodifiableList_thenSuccess() {
        List<String> givenList = Arrays.asList("a", "b", "c");
        List<String> result = givenList.stream()
            .collect(toUnmodifiableList());

        System.out.println(result.getClass());
    }
}
