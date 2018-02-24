package com.baeldung.injectiontypes.domain;


public class Moon {
    private Integer diameter;

    public Moon(Integer diameter) {
        this.diameter = diameter;
    }

    public Integer getDiameter() {
        return diameter;
    }
}
