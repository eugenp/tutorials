package com.baeldung.injectiontypes.domain;


public class Jupiter {
    private Integer diameter;

    public Jupiter(Integer diameter) {
        this.diameter = diameter;
    }

    public Integer getDiameter() {
        return diameter;
    }
}
