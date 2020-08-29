package com.baeldung.hexagonal.architecture.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.architecture.entity.Student;
import com.baeldung.hexagonal.architecture.repository.StudentRepository;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private List<Student> students = new ArrayList<>();

    @Override
    public void createStudent(Student student) {
        students.add(student);
    }

    @Override
    public Optional<Student> getStudent(int id) {
        return students
            .stream()
            .filter(s -> s.getId() == id)
            .findFirst();
    }

    @Override
    public List<Student> getAllStudents() {
        return students;
    }

}
