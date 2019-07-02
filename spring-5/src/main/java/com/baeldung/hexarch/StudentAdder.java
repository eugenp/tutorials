package com.baeldung.hexarch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentAdder implements IStudentAdder {

    @Autowired
    protected IStudentDataRepository studentDataRepository;

    public int add(Student student) {

        return studentDataRepository.add(student);

    }
}
