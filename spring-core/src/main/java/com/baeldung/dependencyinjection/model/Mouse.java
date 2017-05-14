package com.baeldung.dependencyinjection.model;

public class Mouse {

    private String mouseType;

    public Mouse(String mouseType) {
        super();
        this.mouseType = mouseType;
    }

    @Override
    public String toString() {
        return "Mouse [mouseType=" + mouseType + "]";
    }

    public String getMouseType() {
        return mouseType;
    }

}
