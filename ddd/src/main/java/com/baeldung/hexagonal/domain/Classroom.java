package com.baeldung.hexagonal.domain;

import java.util.ArrayList;
import java.util.List;

public class Classroom {
    String name;
    List<Student> students;

    public Classroom(String name) {
        this.name = name;
        students = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Classroom removeStudent(Student student) {
        students.remove(student);
        return this;
    }

    public Classroom addStudent(Student student) {
        students.add(student);
        return this;
    }
}