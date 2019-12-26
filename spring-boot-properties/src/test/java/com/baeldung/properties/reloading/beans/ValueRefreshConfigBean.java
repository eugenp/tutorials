package com.baeldung.properties.reloading.beans;

public class ValueRefreshConfigBean {
    private String color;

    public ValueRefreshConfigBean(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
