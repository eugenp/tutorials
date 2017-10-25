package com.baeldung.beaninjectiontypes.domain;

public class RAM {
    private String id;
    private String capacity;

    public RAM(String id, String capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    @Override public String toString() {
        return String.format("id %s capacity %s", id, capacity);
    }
}
