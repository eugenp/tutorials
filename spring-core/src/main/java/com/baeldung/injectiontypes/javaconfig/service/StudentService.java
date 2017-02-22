package com.baeldung.injectiontypes.javaconfig.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.injectiontypes.dao.StudentDAO;
import com.baeldung.injectiontypes.domain.Student;

@Component
public class StudentService {

    
    StudentDAO dao;

    public StudentService() {
    }

    @Autowired // to test setter injection comment this line and uncomment line 23
    public StudentService(StudentDAO dao) {
        this.dao = dao;
    }

    //@Autowired
    public void setDao(StudentDAO dao) {
        this.dao = dao;
    }

    public void addStudent(Student std) {
        dao.insert(std);
    }

}
