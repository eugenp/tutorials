package com.baeldung.hexagonal.studentapp;

import java.util.List;

public interface StudentRepository {

    Student addStudent(Student student);

    List<Student> getStudents();
}
