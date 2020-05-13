package com.baeldung.architecture.hexagonal.domain;

import java.util.Objects;

public class BikeId {
    private final int id;

    public BikeId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BikeId bikeId = (BikeId) o;
        return id == bikeId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "BikeId{" + "id=" + id + '}';
    }
}
