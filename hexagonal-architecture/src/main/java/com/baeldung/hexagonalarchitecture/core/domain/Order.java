package com.baeldung.hexagonalarchitecture.core.domain;

import java.text.MessageFormat;

public class Order {

    public Long id;

    public String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return MessageFormat.format("[Order: id={0}, name={1}]", id, name);
    }

}
