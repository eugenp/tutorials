package com.baeldung.collectionsvsarrays;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Tasks {

    private static final List<Task> tasks;
    public static final Supplier<List<Task>> supplier;

    static {
        tasks = new ArrayList<>();
        tasks.add(new Task(1, 1, "2023-09-01"));
        tasks.add(new Task(2, 2, "2023-08-30"));
        tasks.add(new Task(3, 1, "2023-08-29"));
        tasks.add(new Task(4, 2, "2023-09-02"));
        tasks.add(new Task(5, 3, "2023-09-05"));
        tasks.add(new Task(6, 1, "2023-09-03"));
        tasks.add(new Task(7, 3, "2023-08-28"));
        tasks.add(new Task(8, 2, "2023-09-01"));
        tasks.add(new Task(9, 1, "2023-08-28"));
        tasks.add(new Task(10, 2, "2023-09-04"));
        tasks.add(new Task(11, 3, "2023-08-31"));
        tasks.add(new Task(12, 1, "2023-08-30"));
        tasks.add(new Task(13, 3, "2023-09-02"));

        supplier = () -> new ArrayList<>(tasks);
    }
}
