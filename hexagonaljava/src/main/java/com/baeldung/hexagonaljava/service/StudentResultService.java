package com.baeldung.hexagonaljava.service;

import com.baeldung.hexagonaljava.entity.Student;

public interface StudentResultService {

    void save(Student student);

    Double getTotalMarks(Integer id);
}
