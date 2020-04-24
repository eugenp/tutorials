package com.baeldung.architecture.hexagonal.dao.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.baeldung.architecture.hexagonal.dao.StudentRepository;
import com.baeldung.architecture.hexagonal.dao.model.Student;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private Map<Integer, Student> students = new HashMap<>();

    public void add(Student student) {
        students.put(student.getRoll(), student);
    }

    public void delete(Student student) {
        students.remove(student.getRoll());
    }

    public Map<Integer, Student> getAll() {
        return students;
    }
}
