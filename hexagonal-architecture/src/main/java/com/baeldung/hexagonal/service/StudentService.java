package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.domain.Student;
import com.baeldung.hexagonal.port.StudentRepositoryPort;

import java.util.UUID;

public class StudentService{

    private final StudentRepositoryPort repository;

    public StudentService(StudentRepositoryPort repository){
        this.repository = repository;
    }

    public String addStudent(String name){
        Student student = new Student();
        String id = String.valueOf(UUID.randomUUID());

        student.setId(id);
        student.setName(name);

        repository.saveStudent(student);

        return id;
    }

    public boolean removeStudent(String id){

        if(null == repository.getStudent(id)) {
            return false;
        }

        repository.deleteStudent(id);

        return true;
    }
}