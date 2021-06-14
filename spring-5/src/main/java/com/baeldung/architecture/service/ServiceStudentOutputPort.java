package com.baeldung.architecture.service;


import com.baeldung.architecture.model.StudentDto;

import java.util.List;

public interface ServiceStudentOutputPort {
    List<StudentDto> findAllStudents();

    StudentDto addStudent(StudentDto studentDto);
}
