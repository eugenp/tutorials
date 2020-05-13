package com.baeldung.architecture.hexagonal.domain;

import java.util.Objects;

public class Bike {
    private BikeId id;
    private boolean isReserved;
    private Category category;
    private Size size;

    public Bike(BikeId id, boolean isReserved, Category category, Size size) {
        this.id = id;
        this.isReserved = isReserved;
        this.category = category;
        this.size = size;
    }

    public BikeId getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Bike))
            return false;
        Bike bike = (Bike) o;
        return isReserved == bike.isReserved && Objects.equals(id, bike.id) && category == bike.category && size == bike.size;
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

    private boolean isAvailable() {
        return !isReserved;
    }

    public Category getCategory() {
        return category;
    }

    public Size getSize() {
        return size;
    }

    boolean isMatchingBike(BikeReservationRequest bikeReservationRequest) {
        boolean isCorrectCategory = this.getCategory() == bikeReservationRequest.getCategory();
        boolean isCorrectSize = this.getSize() == bikeReservationRequest.getSize();

        return this.isAvailable() && isCorrectCategory && isCorrectSize;
    }

    public void markReserved() {
        this.isReserved = true;
    }
}
