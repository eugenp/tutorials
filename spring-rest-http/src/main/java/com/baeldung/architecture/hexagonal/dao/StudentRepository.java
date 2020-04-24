package com.baeldung.architecture.hexagonal.dao;

import java.util.Map;
import com.baeldung.architecture.hexagonal.dao.model.Student;

public interface StudentRepository {

    void add(Student student);

    void delete(Student student);

    Map<Integer, Student> getAll();
}
