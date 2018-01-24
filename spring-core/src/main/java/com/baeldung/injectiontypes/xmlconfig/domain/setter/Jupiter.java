package com.baeldung.injectiontypes.xmlconfig.domain.setter;


public class Jupiter {
    private Integer diameter;

    public Jupiter(Integer diameter) {
        this.diameter = diameter;
    }

    public Integer getDiameter() {
        return diameter;
    }

    public void setDiameter(Integer diameter) {
        this.diameter = diameter;
    }
}
