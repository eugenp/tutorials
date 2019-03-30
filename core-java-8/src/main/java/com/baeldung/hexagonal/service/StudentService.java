package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.entity.Student;

public interface StudentService {

    public Student createStudent(Student student);

    public Student retrieveStudentById(int id);

}
