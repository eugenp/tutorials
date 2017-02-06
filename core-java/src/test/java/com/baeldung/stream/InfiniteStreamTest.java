package com.baeldung.stream;


import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class InfiniteStreamTest {

    @Test
    public void givenInfiniteStream_whenUseIntermediateLimitMethod_thenShouldTerminateInFiniteTime() {
        //given
        Stream<Integer> infiniteStream = Stream.iterate(0, i -> i + 2);

        //when
        List<Integer> collect = infiniteStream
                .limit(10)
                .collect(Collectors.toList());

        //then
        assertEquals(collect, Arrays.asList(0, 2, 4, 6, 8, 10, 12, 14, 16, 18));
    }

}
