package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.domain.Student;
import com.baeldung.hexagonal.port.StudentRepositoryPort;

import java.util.HashMap;
import java.util.Map;

public class StudentInMemoryRepositoryAdapter implements StudentRepositoryPort{

    private Map<String,Student> students = new HashMap<>();

    @Override
    public void saveStudent(Student student) {
        students.put(student.getId(), student);
    }

    @Override
    public Student getStudent(String id) {
        return students.get(id);
    }

    @Override
    public void deleteStudent(String id) {
        students.remove(id);
    }
}