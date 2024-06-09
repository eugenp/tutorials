package com.baeldung.deepshallowcopy.domain;

public class Course {
    private String name;

    public Course(String name) {
        this.name = name;
    }

    public Course(Course course) {
        this.name = course.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
