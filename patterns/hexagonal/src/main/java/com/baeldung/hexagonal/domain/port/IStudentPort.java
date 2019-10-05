package com.baeldung.hexagonal.domain.port;

import com.baeldung.hexagonal.domain.dto.Student;

import java.util.List;

public interface IStudentPort {

    List<Student> getStudents();
}
