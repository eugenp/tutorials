package com.baeldung.hexagonal.inboundports;

import com.baeldung.hexagonal.domain.Student;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    Student getStudentById(int id);
    void createStudent(Student student);
    boolean deleteStudent(Student student);
}
