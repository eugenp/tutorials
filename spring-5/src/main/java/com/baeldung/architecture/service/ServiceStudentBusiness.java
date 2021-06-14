package com.baeldung.architecture.service;

import com.baeldung.architecture.model.StudentDto;

import java.util.List;

public interface ServiceStudentBusiness {
    List<StudentDto> findStudents();

    StudentDto addStudent(StudentDto studentDto);
}
