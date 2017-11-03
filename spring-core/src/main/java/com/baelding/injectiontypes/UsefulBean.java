package com.baelding.injectiontypes;

public class UsefulBean {

    private String firstParameter;
    private String secondParameter;

    public UsefulBean(String firstParameter, String secondParameter) {
        this.firstParameter = firstParameter;
        this.secondParameter = secondParameter;
    }

    public String getParameters() {
        return firstParameter + "-" + secondParameter;
    }

}
