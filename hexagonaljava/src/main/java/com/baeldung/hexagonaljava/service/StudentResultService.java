package com.baeldung.hexagonaljava.service;

import com.baeldung.hexagonaljava.entity.Student;

public interface StudentResultService {

    public void save(Student student);

    public Double getTotalMarks(Integer id);

}
