package com.baeldung.shallowdeepcopy;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Student implements Cloneable, Serializable {
    private String name;
    private List<String> courses = new ArrayList<>();

    public Student(String name, List<String> courses) {
        this.name = name;
        this.courses = courses;
    }

    public Student() {
    }
    public Student(Student other) {
        this.name = other.name;
        this.courses = new ArrayList<>(other.courses);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }
}
