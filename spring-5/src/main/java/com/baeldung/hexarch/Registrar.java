package com.baeldung.hexarch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Registrar {

    @Autowired
    protected IStudentLookup studentLookup;

    @Autowired
    protected IStudentAdder studentAdder;

    public int addStudent(Student student) {
        return studentAdder.add(student);
    }

    public Student findStudent(int id) {
        return studentLookup.find(id);
    }
}