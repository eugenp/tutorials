package com.baeldung.deepshallowcopy.domain;

import java.util.ArrayList;
import java.util.List;

public class StudentSallowCopy {
    private String name;
    private List<Course> courses;

    public StudentSallowCopy(String name) {
        this.name = name;
        this.courses = new ArrayList<>();
    }

    public StudentSallowCopy(StudentSallowCopy studentSallowCopy) {
        this.name = studentSallowCopy.name;
        this.courses = studentSallowCopy.courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }
}
