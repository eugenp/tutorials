package com.baeldung.mockito.construction;

public class Grinder {

    private String beans;

    public Grinder() {
        this.beans = "Guatemalan";
    }

    public String getBeans() {
        return beans;
    }

    public void setBeans(String beans) {
        this.beans = beans;
    }

}