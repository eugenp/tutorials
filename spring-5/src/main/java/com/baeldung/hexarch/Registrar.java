package com.baeldung.hexarch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Registrar {

    @Autowired
    protected IStudentOps studentOps;

    public int addStudent(Student student) {
        return studentOps.add(student);
    }

    public Student findStudent(int id) {
        return studentOps.find(id);
    }
}