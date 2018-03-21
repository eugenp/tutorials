package com.baeldung.injectiontypes;

public class Student {

    private long rollNumber;

    private String name;

    Student(final long rollNumber, final String name) {

        this.rollNumber = rollNumber;
        this.name = name;
    }

    public long getRollNumber() {

        return rollNumber;
    }

    public String getName() {

        return name;
    }

}
