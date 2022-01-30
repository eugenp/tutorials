package com.baeldung.architecturehexagonal.domain.model;

import java.util.Objects;

public class Table {
    private final Long id;
    private final Integer number;
    private final Integer capacity;

    public Table(Long id, Integer number, Integer capacity) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
    }

    public static Table of(Long id, Integer number, Integer capacity) {
        return new Table(id, number, capacity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Table table = (Table) o;
        return number.equals(table.number) && capacity.equals(table.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, capacity);
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Long getId() {
        return id;
    }
}
