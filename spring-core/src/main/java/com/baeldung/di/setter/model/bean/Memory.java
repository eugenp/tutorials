package com.baeldung.di.setter.model.bean;

public class Memory {

    private Integer sizeInGb = 16;
    private String format = "DDR3";

    //
    public Integer getSizeInGb() {
        return sizeInGb;
    }

    public String getFormat() {
        return format;
    }

}
