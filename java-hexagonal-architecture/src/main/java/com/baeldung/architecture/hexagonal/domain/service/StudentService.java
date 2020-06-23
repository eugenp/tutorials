package com.baeldung.architecture.hexagonal.domain.service;

import com.baeldung.architecture.hexagonal.domain.Student;

//Inbound Port
public interface StudentService {

    public Long enrolStudent(Student student);

    public void unregisterStudent(Long id);

    public String evaluatePerformance(Long id);

}
