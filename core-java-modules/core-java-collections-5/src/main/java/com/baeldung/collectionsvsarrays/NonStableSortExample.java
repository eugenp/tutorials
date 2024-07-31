package com.baeldung.collectionsvsarrays;

import com.baeldung.collectionsvsarrays.sorting.Quicksort;
import java.util.Comparator;
import java.util.List;

public class NonStableSortExample {



    public static void main(String[] args) {
        List<Task> tasks = Tasks.supplier.get();
        Quicksort.sort(tasks, Comparator.comparingInt(Task::getPriority));
        System.out.println("After sorting by priority:");
        for (Task task : tasks) {
            System.out.println(task);
        }
        Quicksort.sort(tasks, Comparator.comparing(Task::getDueDate));
        System.out.println("\nAfter sorting by due date:");
        for (Task task : tasks) {
            System.out.println(task);
        }
    }


}
