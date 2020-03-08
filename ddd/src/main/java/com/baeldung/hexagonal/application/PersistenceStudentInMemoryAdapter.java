package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.Student;

import java.util.HashMap;
import java.util.Map;

public class PersistenceStudentInMemoryAdapter implements PersistenceStudentPort {
    Map<String, Student> classroomByName;

    PersistenceStudentInMemoryAdapter() {
        this.classroomByName = new HashMap<>();
    }

    @Override
    public void save(Student student) {
        this.classroomByName.put(student.getName(), student);
    }

    @Override
    public Student find(String name) {
        return classroomByName.get(name);
    }
}
