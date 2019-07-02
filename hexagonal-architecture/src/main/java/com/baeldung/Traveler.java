package com.baeldung;

public class Traveler {

    private String name;
    private boolean hasCriminalRecord;

    public boolean hasCriminalRecord() {
        return hasCriminalRecord;
    }

    public Traveler(String name, boolean hasCriminalRecord) {
        this.name = name;
        this.hasCriminalRecord = hasCriminalRecord;
    }

    public String getName() {
        return name;
    }

}
