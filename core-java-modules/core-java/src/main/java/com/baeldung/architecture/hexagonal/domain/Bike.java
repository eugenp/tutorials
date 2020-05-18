package com.baeldung.architecture.hexagonal.domain;

import java.util.Objects;

public class Bike {
    private int id;
    private boolean isReserved;
    private Category category;
    private Size size;

    public Bike(int id, boolean isReserved, Category category, Size size) {
        this.id = id;
        this.isReserved = isReserved;
        this.category = category;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Bike))
            return false;
        Bike bike = (Bike) o;
        return id == bike.id && isReserved == bike.isReserved && category == bike.category && size == bike.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isReserved, category, size);
    }

    @Override
    public String toString() {
        return "Bike{" + "id=" + id + ", isReserved=" + isReserved + ", category=" + category + ", size=" + size + '}';
    }

    public boolean isReserved() {
        return isReserved;
    }

    public boolean isAvailable() {
        return !isReserved;
    }

    public Category getCategory() {
        return category;
    }

    public Size getSize() {
        return size;
    }

    public void markReserved() {
        this.isReserved = true;
    }
}
