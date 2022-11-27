package com.baeldung.collections.priorityqueue;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.PriorityQueue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PriorityQueueComparatorUnitTest {

    @Test
    void givenIntegerQueue_defaultComparator_followsNaturalOrdering() {
        PriorityQueue<Integer> integerQueue = new PriorityQueue<>();
        PriorityQueue<Integer> integerQueueWithComparator = new PriorityQueue<>((Integer c1, Integer c2) -> Integer.compare(c1, c2));

        integerQueueWithComparator.add(3);
        integerQueue.add(3);

        integerQueueWithComparator.add(2);
        integerQueue.add(2);

        integerQueueWithComparator.add(1);
        integerQueue.add(1);

        assertThat(integerQueue.poll()).isEqualTo(1).isEqualTo(integerQueueWithComparator.poll());
        assertThat(integerQueue.poll()).isEqualTo(2).isEqualTo(integerQueueWithComparator.poll());
        assertThat(integerQueue.poll()).isEqualTo(3).isEqualTo(integerQueueWithComparator.poll());
    }

    @Test
    void givenIntegerQueue_reverseOrderComparator_followsInverseNaturalOrdering() {
        PriorityQueue<Integer> reversedQueue = new PriorityQueue<>(Collections.reverseOrder());

        reversedQueue.add(1);
        reversedQueue.add(2);
        reversedQueue.add(3);

        assertThat(reversedQueue.poll()).isEqualTo(3);
        assertThat(reversedQueue.poll()).isEqualTo(2);
        assertThat(reversedQueue.poll()).isEqualTo(1);
    }

    @Test
    void givenNotComparableQueue_classCastException() {
        assertThatThrownBy(() -> {
            PriorityQueue<ColoredNumber> queue = new PriorityQueue<>();
            queue.add(new ColoredNumber(3, "red"));
            queue.add(new ColoredNumber(2, "blue"));
        }).isInstanceOf(ClassCastException.class);
    }

    @Test
    void givenCustomOrderingQueue_orderIsCorrect() {
        PriorityQueue<ColoredNumberComparable> queue = new PriorityQueue<>();
        queue.add(new ColoredNumberComparable(10, "red"));
        queue.add(new ColoredNumberComparable(20, "red"));
        queue.add(new ColoredNumberComparable(1, "blue"));
        queue.add(new ColoredNumberComparable(2, "blue"));

        ColoredNumberComparable first = queue.poll();
        assertThat(first.getColor()).isEqualTo("red");
        assertThat(first.getValue()).isEqualTo(10);

        queue.poll();

        ColoredNumberComparable third = queue.poll();
        assertThat(third.getColor()).isEqualTo("blue");
        assertThat(third.getValue()).isEqualTo(1);
    }

}

