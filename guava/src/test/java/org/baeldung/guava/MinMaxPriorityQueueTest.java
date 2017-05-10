package org.baeldung.guava;


import com.google.common.collect.MinMaxPriorityQueue;
import org.junit.Test;

import java.util.Comparator;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class MinMaxPriorityQueueTest {
    @Test
    public void givenMinMaxPriorityQueue_whenAddElementToFull_thenShouldEvictOldestItem() {
        //given
        MinMaxPriorityQueue<CustomClass> queue = MinMaxPriorityQueue
                .orderedBy(Comparator.comparing(CustomClass::getValue).reversed())
                .maximumSize(10)
                .create();

        //when
        IntStream
                .iterate(10, i -> i - 1)
                .limit(10)
                .forEach(i -> queue.add(new CustomClass(i)));

        //then
        assertThat(queue.peekFirst().getValue()).isEqualTo(10);
        assertThat(queue.peekLast().getValue()).isEqualTo(1);


        //and
        queue.add(new CustomClass(100));

        //then
        assertThat(queue.peekFirst().getValue()).isEqualTo(100);
        assertThat(queue.peekLast().getValue()).isEqualTo(2);

        //and
        queue.add(new CustomClass(-1));
        assertThat(queue.peekFirst().getValue()).isEqualTo(100);
        assertThat(queue.peekLast().getValue()).isEqualTo(2);
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
