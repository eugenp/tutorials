package com.baeldung.comparable;

import java.util.Objects;

/**
 * Same idea as {@link SimpleTask}, but intentionally does NOT implement {@link Comparable}.
 * This is useful to demonstrate ordering using a {@link java.util.Comparator} with TreeSet/PriorityQueue.
 */
public class NonComparableTask {

    private final String name;
    private final int priority;

    public NonComparableTask(String name, int priority) {
        this.name = Objects.requireNonNull(name, "name must not be null");
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NonComparableTask)) {
            return false;
        }
        NonComparableTask that = (NonComparableTask) o;
        return priority == that.priority && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, priority);
    }

    @Override
    public String toString() {
        return "NonComparableTask{name='" + name + "', priority=" + priority + "}";
    }
}

