package com.baeldung.injectiontypes.javaconfig.domain.constructor;


public class Earth {
    private Integer diameter;

    public Earth(Integer diameter) {
        this.diameter = diameter;
    }

    public Integer getDiameter() {
        return diameter;
    }

    public void setDiameter(Integer diameter) {
        this.diameter = diameter;
    }
}
