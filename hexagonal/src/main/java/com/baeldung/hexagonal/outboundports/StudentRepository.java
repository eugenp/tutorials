package com.baeldung.hexagonal.outboundports;

import com.baeldung.hexagonal.domain.Student;

import java.util.List;

public interface StudentRepository {
    List<Student> getAllStudents();
    Student getStudentById(int id);
    void createStudent(Student student);
    boolean deleteStudent(Student student);
}
