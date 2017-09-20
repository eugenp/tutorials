package com.baeldung.beaninjection;

import java.util.Objects;

public class Librarian {
    private String name;

    public Librarian(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Librarian librarian = (Librarian) o;
        return Objects.equals(name, librarian.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
