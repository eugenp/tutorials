package com.baeldung.streams;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StreamToImmutableUnitTest {

    @Test
    public void whenUsingStreamToList_thenReturnImmutableList() {

        List<String> immutableList = Stream.of("a", "b", "c", "d")
            .toList();

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            immutableList.add("e");
        });

    }

}
