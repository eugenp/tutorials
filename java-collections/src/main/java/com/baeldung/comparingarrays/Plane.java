package com.baeldung.comparingarrays;

import java.util.Objects;

public class Plane {

    private final String name;

    private final String model;

    public Plane(String name, String model) {

        this.name = name;
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Plane plane = (Plane) o;
        return Objects.equals(name, plane.name) && Objects.equals(model, plane.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, model);
    }
}
