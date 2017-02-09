package com.baeldung.beaninjection;

public class AnotherSampleDAOBean implements IAnotherSampleDAO {

    private String propertyY;

    public AnotherSampleDAOBean(String propertyY) {
        this.propertyY = propertyY;
    }

    // standard setters and getters

    public String getPropertyY() {
        return propertyY;
    }

}
