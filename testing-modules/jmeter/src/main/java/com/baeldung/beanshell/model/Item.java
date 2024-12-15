package com.baeldung.beanshell.model;

import java.util.UUID;

public class Item {

    private UUID id;
    private String key;
    private Integer value;

    public Item() {
        this.id = UUID.randomUUID();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public UUID getId() {
        return id;
    }
}
