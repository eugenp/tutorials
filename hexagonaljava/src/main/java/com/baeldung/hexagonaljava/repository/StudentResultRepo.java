package com.baeldung.hexagonaljava.repository;

import com.baeldung.hexagonaljava.entity.Student;

public interface StudentResultRepo {

    void save(Student student);

    Student getStudent(Integer id);

}
