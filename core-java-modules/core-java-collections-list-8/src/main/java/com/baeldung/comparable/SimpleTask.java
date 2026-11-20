package com.baeldung.comparable;

import java.util.Objects;

/**
 * A tiny domain object that can be ordered.
 * Ordered by {@code priority} ascending, then by {@code name} lexicographically.
 */
public class SimpleTask implements Comparable<SimpleTask> {

    private final String name;
    private final int priority;

    public SimpleTask(String name, int priority) {
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
    public int compareTo(SimpleTask other) {
        if (other == null) {
            throw new NullPointerException("other must not be null");
        }
        int byPriority = Integer.compare(this.priority, other.priority);
        if (byPriority != 0) {
            return byPriority;
        }
        return this.name.compareTo(other.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SimpleTask)) {
            return false;
        }
        SimpleTask that = (SimpleTask) o;
        return priority == that.priority && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, priority);
    }

    @Override
    public String toString() {
        return "SimpleTask{name='" + name + "', priority=" + priority + "}";
    }
}

