package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.entity.Student;

public interface StudentUserInterfacePort {

    public Student createStudent(Student student);

    public Student getStudent(int id);

}
