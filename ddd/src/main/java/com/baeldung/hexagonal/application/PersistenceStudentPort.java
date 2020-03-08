package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.Student;

public interface PersistenceStudentPort {
    void save(Student student);
    Student find(String name);
}
