package com.baeldung.hexagonal.studentapp;

import java.util.List;

public interface StudentService {

    Student addStudent(Student student);

    List<Student> getStudents();
}
