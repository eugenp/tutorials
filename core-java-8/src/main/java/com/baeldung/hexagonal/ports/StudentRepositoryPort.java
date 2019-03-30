package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.entity.Student;

public interface StudentRepositoryPort {

    public Student saveStudent(Student student);

    public Student getStudent(int id);

}
