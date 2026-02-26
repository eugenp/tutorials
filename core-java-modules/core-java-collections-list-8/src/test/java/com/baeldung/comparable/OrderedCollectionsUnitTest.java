package com.baeldung.comparable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class OrderedCollectionsUnitTest {

    @Test
    void givenTasksInRandomOrder_whenAddedToPriorityQueue_thenPollingReturnsSortedOrder() {
        PriorityQueue<SimpleTask> pq = new PriorityQueue<>();

        pq.add(new SimpleTask("Write docs", 3));
        pq.add(new SimpleTask("Fix build", 1));
        pq.add(new SimpleTask("Review PR", 2));
        pq.add(new SimpleTask("Another P1", 1));

        List<SimpleTask> polled = new ArrayList<>();
        while (!pq.isEmpty()) {
            polled.add(pq.poll());
        }

        assertEquals(
            Arrays.asList(
                new SimpleTask("Another P1", 1),
                new SimpleTask("Fix build", 1),
                new SimpleTask("Review PR", 2),
                new SimpleTask("Write docs", 3)
            ),
            polled
        );
    }

    @Test
    void givenTasksInRandomOrder_whenAddedToTreeSet_thenIterationIsSorted() {
        TreeSet<SimpleTask> set = new TreeSet<>();

        set.add(new SimpleTask("Write docs", 3));
        set.add(new SimpleTask("Fix build", 1));
        set.add(new SimpleTask("Review PR", 2));
        set.add(new SimpleTask("Fix build", 1)); // duplicate (same priority + name)

        assertEquals(
            List.of(
                new SimpleTask("Fix build", 1),
                new SimpleTask("Review PR", 2),
                new SimpleTask("Write docs", 3)
            ),
            new ArrayList<>(set)
        );
    }

    @Test
    void givenNonComparableTasks_whenUsingTreeSetWithComparator_thenIterationIsSorted() {
        Comparator<NonComparableTask> byPriorityThenName = Comparator.comparingInt(NonComparableTask::getPriority)
            .thenComparing(NonComparableTask::getName);

        TreeSet<NonComparableTask> set = new TreeSet<>(byPriorityThenName);
        set.add(new NonComparableTask("Write docs", 3));
        set.add(new NonComparableTask("Fix build", 1));
        set.add(new NonComparableTask("Review PR", 2));

        assertEquals(
            Arrays.asList(
                new NonComparableTask("Fix build", 1),
                new NonComparableTask("Review PR", 2),
                new NonComparableTask("Write docs", 3)
            ),
            new ArrayList<>(set)
        );
    }

    @Test
    void givenNonComparableTasks_whenUsingPriorityQueueWithComparator_thenPollingReturnsSortedOrder() {
        Comparator<NonComparableTask> byPriorityThenName = Comparator.comparingInt(NonComparableTask::getPriority)
            .thenComparing(NonComparableTask::getName);

        PriorityQueue<NonComparableTask> pq = new PriorityQueue<>(byPriorityThenName);
        pq.add(new NonComparableTask("Write docs", 3));
        pq.add(new NonComparableTask("Fix build", 1));
        pq.add(new NonComparableTask("Review PR", 2));

        List<NonComparableTask> polled = new ArrayList<>();
        while (!pq.isEmpty()) {
            polled.add(pq.poll());
        }

        assertEquals(
            Arrays.asList(
                new NonComparableTask("Fix build", 1),
                new NonComparableTask("Review PR", 2),
                new NonComparableTask("Write docs", 3)
            ),
            polled
        );
    }

    @Test
    void givenNonComparableTasks_whenUsingPriorityQueueWithoutComparator_thenAddingThrowsClassCastException() {
        PriorityQueue<NonComparableTask> pq = new PriorityQueue<>();
        ClassCastException ex = assertThrows(ClassCastException.class, () -> pq.add(new NonComparableTask("First", 1)));
        assertEquals(ClassCastException.class, ex.getClass());
    }

    @Test
    void givenNonComparableTasks_whenUsingTreeSetWithoutComparator_thenAddingThrowsClassCastException() {
        TreeSet<NonComparableTask> set = new TreeSet<>();
        ClassCastException ex = assertThrows(ClassCastException.class, () -> set.add(new NonComparableTask("Fix build", 1)));
        assertEquals(ClassCastException.class, ex.getClass());
    }
}

