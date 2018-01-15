package com.baeldung.tutorial.beaninjection.beans;

public class Demographic {

    private String city;
    private String state;
    private Integer zipCode;

    public Demographic(String ct, String st, String zip) {
        state = st;
        city = ct;
        zipCode = Integer.parseInt(zip);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }
}
