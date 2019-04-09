package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.Student;

public interface StudentRepositoryPort{

    void saveStudent(Student student);
    Student getStudent(String id);
    void deleteStudent(String id);
}