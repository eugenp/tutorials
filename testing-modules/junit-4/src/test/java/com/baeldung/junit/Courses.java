package com.baeldung.junit;
public class Courses {
    String courseName;
    public Courses(String courseName) {
        this.courseName = courseName;
    }
    public Courses(Courses other) {
        this.courseName = other.courseName;
    }
}
