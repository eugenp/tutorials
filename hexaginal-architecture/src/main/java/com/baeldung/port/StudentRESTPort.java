package com.baeldung.port;

import java.util.List;

import com.baeldung.model.Student;

public interface StudentRESTPort {
    public List<Student> getAllStudents();

    public Student registerStudent(Student student);
}
