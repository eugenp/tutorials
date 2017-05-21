package org.baeldung.guava;


import com.google.common.collect.EvictingQueue;
import org.junit.Test;

import java.util.Queue;
import java.util.stream.IntStream;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class EvictingQueueUnitTest {

    @Test
    public void givenEvictingQueue_whenAddElementToFull_thenShouldEvictOldestItem() {
        //given
        Queue<Integer> evictingQueue = EvictingQueue.create(10);

        //when
        IntStream.range(0, 10).forEach(evictingQueue::add);

        //then
        assertThat(evictingQueue).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        //and
        evictingQueue.add(100);

        //then
        assertThat(evictingQueue).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 100);
    }
}
