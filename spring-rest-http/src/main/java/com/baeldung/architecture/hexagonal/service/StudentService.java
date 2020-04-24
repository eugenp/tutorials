package com.baeldung.architecture.hexagonal.service;

import java.util.Map;
import com.baeldung.architecture.hexagonal.dao.model.Student;

public interface StudentService {

    void add(Student student);

    void delete(Student student);

    Map<Integer, Student> getAll();
}
