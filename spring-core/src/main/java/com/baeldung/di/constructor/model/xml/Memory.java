package com.baeldung.di.constructor.model.xml;

public class Memory {

    private Integer sizeInGb = 16;
    private String format = "DDR3";

    public Memory() {

    }

    public Integer getSizeInGb() {
        return sizeInGb;
    }

    public String getFormat() {
        return format;
    }

}
