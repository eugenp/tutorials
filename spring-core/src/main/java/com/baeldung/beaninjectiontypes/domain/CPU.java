package com.baeldung.beaninjectiontypes.domain;

public class CPU {
    private String id;
    private String model;

    public CPU(String id, String model) {
        this.id = id;
        this.model = model;
    }

    @Override
    public String toString() {
        return String.format("id %s model %s", id, model);
    }
}
