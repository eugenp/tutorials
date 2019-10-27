package com.baeldung.breakforeach;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class BreakFromStreamForEachUnitTest {

    @Test
    public void whenCustomTakeWhileIsCalled_ThenCorrectItemsAreReturned() {
        Stream<String> initialStream = Stream.of("cat", "dog", "elephant", "fox", "rabbit", "duck");

        List<String> result = CustomTakeWhile.takeWhile(initialStream, x -> x.length() % 2 != 0)
                .collect(Collectors.toList());

        assertEquals(asList("cat", "dog"), result);
    }

    @Test
    public void whenCustomForEachIsCalled_ThenCorrectItemsAreReturned() {
        Stream<String> initialStream = Stream.of("cat", "dog", "elephant", "fox", "rabbit", "duck");
        List<String> result = new ArrayList<>();

        CustomForEach.forEach(initialStream, (elem, breaker) -> {
            if (elem.length() % 2 == 0) {
                breaker.stop();
            } else {
                result.add(elem);
            }
        });

        assertEquals(asList("cat", "dog"), result);
    }

}
