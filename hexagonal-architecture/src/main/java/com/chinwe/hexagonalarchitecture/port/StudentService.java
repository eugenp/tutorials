package com.chinwe.hexagonalarchitecture.port;

import com.chinwe.hexagonalarchitecture.domain.model.Student;
import com.chinwe.hexagonalarchitecture.domain.responsestatus.Status;

import java.util.List;

public interface StudentService {

    List<Student> getStudents();
    Student getStudentById(Long studentId);
    Status addStudent(Student student);
    Status removeStudent(Long studentId);
}
