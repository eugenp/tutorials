package com.baeldung.guava.queues;


import com.google.common.collect.MinMaxPriorityQueue;
import org.junit.Test;

import java.util.Comparator;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class MinMaxPriorityQueueUnitTest {
    @Test
    public void givenMinMaxPriorityQueue_whenAddElementToFull_thenShouldEvictGreatestItem() {
        //given
        MinMaxPriorityQueue<CustomClass> queue = MinMaxPriorityQueue
                .orderedBy(Comparator.comparing(CustomClass::getValue))
                .maximumSize(10)
                .create();

        //when
        IntStream
                .iterate(10, i -> i - 1)
                .limit(10)
                .forEach(i -> queue.add(new CustomClass(i)));

        //then
        assertThat(queue.peekFirst().getValue()).isEqualTo(1);
        assertThat(queue.peekLast().getValue()).isEqualTo(10);


        //and
        queue.add(new CustomClass(-1));


        //then
        assertThat(queue.peekFirst().getValue()).isEqualTo(-1);
        assertThat(queue.peekLast().getValue()).isEqualTo(9);

        //and
        queue.add(new CustomClass(100));
        assertThat(queue.peekFirst().getValue()).isEqualTo(-1);
        assertThat(queue.peekLast().getValue()).isEqualTo(9);

    }


    class CustomClass {
        private final Integer value;

        CustomClass(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "CustomClass{" +
                    "value=" + value +
                    '}';
        }
    }
}
