package com.baeldung.guava.tutorial;

import com.google.common.util.concurrent.Monitor;

import java.util.ArrayList;
import java.util.List;

public class MonitorExample {
    private List<String> students = new ArrayList<String>();
    private static final int MAX_SIZE = 100;

    private Monitor monitor = new Monitor();

    public void addToCourse(String item) throws InterruptedException {
        Monitor.Guard studentsBelowCapacity = monitor.newGuard(this::isStudentsCapacityUptoLimit);
        monitor.enterWhen(studentsBelowCapacity);
        try {
            students.add(item);
        } finally {
            monitor.leave();
        }
    }

    public Boolean isStudentsCapacityUptoLimit() {
        return students.size() > MAX_SIZE;
    }
}
