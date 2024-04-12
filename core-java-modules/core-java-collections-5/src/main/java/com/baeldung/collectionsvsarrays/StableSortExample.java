package com.baeldung.collectionsvsarrays;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StableSortExample {

    public static void main(String[] args) {
        final List<Task> tasks = Tasks.supplier.get();
        Collections.sort(tasks, Comparator.comparingInt(Task::getPriority));
        System.out.println("After sorting by priority:");
        for (Task task : tasks) {
            System.out.println(task);
        }
        Collections.sort(tasks, Comparator.comparing(Task::getDueDate));
        System.out.println("\nAfter sorting by due date:");
        for (Task task : tasks) {
            System.out.println(task);
        }
    }
}
