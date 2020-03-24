package com.baeldung.performancetests.model.destination;

import com.googlecode.jmapper.annotations.JMap;

public class DestinationCode {
    @JMap
    String code;

    public DestinationCode(String code) {
        this.code = code;
    }

    public DestinationCode() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
