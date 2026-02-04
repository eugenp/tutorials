package com.baeldung.comparable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class ListSortWithComparatorUnitTest {

    private static final Comparator<NonComparableTask> BY_PRIORITY_THEN_NAME =
        Comparator.comparingInt(NonComparableTask::getPriority)
            .thenComparing(NonComparableTask::getName);

    @Test
    void givenArrayListOfNonComparableTasks_whenSortedWithComparator_thenOrderIsCorrect() {
        List<NonComparableTask> tasks = new ArrayList<>();
        tasks.add(new NonComparableTask("Write docs", 3));
        tasks.add(new NonComparableTask("Fix build", 1));
        tasks.add(new NonComparableTask("Review PR", 2));
        tasks.add(new NonComparableTask("Another P1", 1));

        tasks.sort(BY_PRIORITY_THEN_NAME);

        assertEquals(
            Arrays.asList(
                new NonComparableTask("Another P1", 1),
                new NonComparableTask("Fix build", 1),
                new NonComparableTask("Review PR", 2),
                new NonComparableTask("Write docs", 3)
            ),
            tasks
        );
    }

    @Test
    void givenLinkedListOfNonComparableTasks_whenSortedWithComparator_thenOrderIsCorrect() {
        List<NonComparableTask> tasks = new LinkedList<>();
        tasks.add(new NonComparableTask("Write docs", 3));
        tasks.add(new NonComparableTask("Fix build", 1));
        tasks.add(new NonComparableTask("Review PR", 2));
        tasks.add(new NonComparableTask("Another P1", 1));

        tasks.sort(BY_PRIORITY_THEN_NAME);

        assertEquals(
            Arrays.asList(
                new NonComparableTask("Another P1", 1),
                new NonComparableTask("Fix build", 1),
                new NonComparableTask("Review PR", 2),
                new NonComparableTask("Write docs", 3)
            ),
            tasks
        );
    }

    @Test
    void givenComparableTasks_whenSortedWithoutComparator_thenNaturalOrderIsUsed() {
        List<SimpleTask> tasks = new ArrayList<>();
        tasks.add(new SimpleTask("Write docs", 3));
        tasks.add(new SimpleTask("Fix build", 1));
        tasks.add(new SimpleTask("Review PR", 2));
        tasks.add(new SimpleTask("Another P1", 1));

        tasks.sort(null); 

        assertEquals(
            Arrays.asList(
                new SimpleTask("Another P1", 1),
                new SimpleTask("Fix build", 1),
                new SimpleTask("Review PR", 2),
                new SimpleTask("Write docs", 3)
            ),
            tasks
        );
    }

    @Test
    void givenComparableTasks_whenSortedWithoutComparator_thenNaturalOrderIsUsedExplicitly() {
        List<SimpleTask> tasks = new ArrayList<>();
        tasks.add(new SimpleTask("Write docs", 3));
        tasks.add(new SimpleTask("Fix build", 1));
        tasks.add(new SimpleTask("Review PR", 2));
        tasks.add(new SimpleTask("Another P1", 1));

        tasks.sort(SimpleTask::compareTo); 

        assertEquals(
            Arrays.asList(
                new SimpleTask("Another P1", 1),
                new SimpleTask("Fix build", 1),
                new SimpleTask("Review PR", 2),
                new SimpleTask("Write docs", 3)
            ),
            tasks
        );
    }

    @Test
    void givenComparableTasks_whenSortedWithNaturalOrder_thenOrderIsCorrect() {
        List<SimpleTask> tasks = new ArrayList<>();
        tasks.add(new SimpleTask("Write docs", 3));
        tasks.add(new SimpleTask("Fix build", 1));
        tasks.add(new SimpleTask("Review PR", 2));
        tasks.add(new SimpleTask("Another P1", 1));

        tasks.sort(Comparator.naturalOrder());

        assertEquals(
            Arrays.asList(
                new SimpleTask("Another P1", 1),
                new SimpleTask("Fix build", 1),
                new SimpleTask("Review PR", 2),
                new SimpleTask("Write docs", 3)
            ),
            tasks
        );
    }

    @Test
    void givenNonComparableTasksInArrayList_whenSortedWithoutComparator_thenThrowsClassCastException() {
        List<NonComparableTask> tasks = new ArrayList<>();
        tasks.add(new NonComparableTask("B", 2));
        tasks.add(new NonComparableTask("A", 1));

        ClassCastException ex = assertThrows(ClassCastException.class, () -> tasks.sort(null));
        assertEquals(ClassCastException.class, ex.getClass());
    }

    @Test
    void givenNonComparableTasksInLinkedList_whenSortedWithoutComparator_thenThrowsClassCastException() {
        List<NonComparableTask> tasks = new LinkedList<>();
        tasks.add(new NonComparableTask("B", 2));
        tasks.add(new NonComparableTask("A", 1));

        ClassCastException ex = assertThrows(ClassCastException.class, () -> tasks.sort(null));
        assertEquals(ClassCastException.class, ex.getClass());
    }

    @Test
    void givenNonComparableTasksInArrayList_whenSortedWithCollectionsSort_thenThrowsClassCastException() {
        ArrayList tasks = new ArrayList<>();
        tasks.add(new NonComparableTask("B", 2));
        tasks.add(new NonComparableTask("A", 1));
        
        ClassCastException ex = assertThrows(ClassCastException.class, () -> Collections.sort(tasks));
        assertEquals(ClassCastException.class, ex.getClass());
    }
}

