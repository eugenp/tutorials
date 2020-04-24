package com.baeldung.architecture.hexagonal.dao.impl;

import com.baeldung.architecture.hexagonal.dao.StudentRepository;
import com.baeldung.architecture.hexagonal.dao.model.Student;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private Map<Integer, Student> students = new HashMap<>();

    public void add(Student student) { students.put(student.getRoll(),student); }

    public void delete(Student student) { students.remove(student.getRoll()); }

    public Map<Integer,Student> getAll() { return students; }
}
