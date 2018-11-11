package com.baeldung.hexagonalarchitecture.domain;

public class Agenda {

    private String name;
    private Integer number;

    public Agenda(String name, Integer number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public void setName(String name) {
        this.name = name;
    }
}
