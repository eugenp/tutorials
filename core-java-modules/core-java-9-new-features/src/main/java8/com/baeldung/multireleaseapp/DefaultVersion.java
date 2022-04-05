package com.baeldung.multireleaseapp;

public class DefaultVersion implements Version {

    @Override
    public String version() {
        return System.getProperty("java.version");
    }
}
