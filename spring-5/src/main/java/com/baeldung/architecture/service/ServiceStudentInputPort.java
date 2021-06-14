package com.baeldung.architecture.service;


import com.baeldung.architecture.model.StudentDto;

public interface ServiceStudentInputPort {
    StudentDto addStudent(StudentDto studentDto);
}
