package com.baeldung.spring.enums.entity;

import com.baeldung.spring.model.Modes;

public class ModeEntity {

    private Long id;

    private String name;

    private Modes mode;

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

    public Modes getMode() {
        return mode;
    }

    public void setMode(Modes mode) {
        this.mode = mode;
    }
}
