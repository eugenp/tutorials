package org.example;

import java.util.Comparator;
import java.util.List;

public record StudentRecord(String name, int rollNo, int marks) {
    public StudentRecord(String name){
        this(name, 0,0);
    }
    public StudentRecord {
        if (marks < 0 || marks > 100) {
            throw new IllegalArgumentException("Marks should be between 0 and 100.");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
    }
}

