package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.port.StudentInputPort;
import com.baeldung.hexagonal.service.StudentService;

public class StudentInputAdapter implements StudentInputPort{

    private final StudentService studentService;

    public StudentInputAdapter(StudentService studentService){
        this.studentService = studentService;
    }

    @Override
    public String registerStudent(String name) {
        return studentService.addStudent(name);
    }

    @Override
    public boolean deregisterStudent(String id) {
        return studentService.removeStudent(id);
    }
}