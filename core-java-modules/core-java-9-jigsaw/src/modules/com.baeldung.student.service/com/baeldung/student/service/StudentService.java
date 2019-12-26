package com.baeldung.student.service;

import com.baeldung.student.model.Student;

public interface StudentService {

    public String create(Student student);

    public Student read(String registrationId);

    public Student update(Student student);

    public String delete(String registrationId);
}