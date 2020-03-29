package com.baeldung.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.model.Student;
import com.baeldung.port.StudentDBPort;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentDBPort studentDBPort;

    @Autowired
    public StudentServiceImpl(StudentDBPort studentDBPort) {
        super();
        this.studentDBPort = studentDBPort;
    }

    @Override
    public List<Student> findAll() {
        return studentDBPort.findAll();
    }

    @Override
    public void save(Student student) {
        studentDBPort.save(student);
    }

}
