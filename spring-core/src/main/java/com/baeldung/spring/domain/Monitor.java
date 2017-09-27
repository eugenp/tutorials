package com.baeldung.spring.domain;

public class Monitor {

    private String size;

    private String resolution;

    public Monitor(String size, String resolution) {
        this.size = size;
        this.resolution = resolution;
    }
    
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

}
