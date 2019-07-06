package com.baeldung.hexarch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentOps implements IStudentOps {

    @Autowired
    protected IStudentDataRepository studentDataRepository;

    @Override
    public Student find(int id) {

        return studentDataRepository.findById(id);

    }

    @Override
    public int add(Student student) {

        return studentDataRepository.add(student);

    }

}