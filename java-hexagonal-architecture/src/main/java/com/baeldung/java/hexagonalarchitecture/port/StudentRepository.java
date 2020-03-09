package com.baeldung.java.hexagonalarchitecture.port;

import java.util.List;

import com.baeldung.java.hexagonalarchitecture.domain.Student;

public interface StudentRepository {

    public void createStudent(Student student);

    public List<Student> listStudent();

}
