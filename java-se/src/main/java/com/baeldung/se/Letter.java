package com.baeldung.se;

public class Letter {
    public String value;
    public Version version;

    public Letter(String value, Version version){

        this.value = value;
        this.version = version;
    }

    public Letter(){}

    public Version getVersion() {
        return version;
    }
}
