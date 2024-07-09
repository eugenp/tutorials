package com.baeldung.junit;
public class Courses1 {
    String courseName;

    public Courses1(String courseName) {
        this.courseName = courseName;
    }

    public Courses1(Courses1 other) {
        this.courseName = other.courseName;
    }
}
