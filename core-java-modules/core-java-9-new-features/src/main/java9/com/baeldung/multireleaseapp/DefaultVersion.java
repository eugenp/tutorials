package com.baeldung.multireleaseapp;

public class DefaultVersion implements Version {

    @Override
    public String version() {
        return Runtime.version().toString();
    }
}
