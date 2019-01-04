package com.baeldung;

import java.util.Objects;

class Friend {

    private String name;

    Friend(String name) {
        this.name = name;
    }

    String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friend friend = (Friend) o;
        return this.name.equals(((Friend) o).name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
