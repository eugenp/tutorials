package com.baeldung.port;

import java.util.List;

import com.baeldung.model.Student;

public interface StudentDBPort {
    public List<Student> findAll();

    public void save(Student student);
}
