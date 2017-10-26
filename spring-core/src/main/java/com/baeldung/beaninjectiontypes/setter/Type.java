package com.baeldung.beaninjectiontypes.setter;

public class Type {

    private String bodyType;
    private int numberOfDoors;

    public Type(String type, int numberOfDoors) {
        this.bodyType = type;
        this.numberOfDoors = numberOfDoors;
    }
}
