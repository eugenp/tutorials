/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.hexagon.service;

import com.baeldung.hexagon.model.Student;
import com.baeldung.hexagon.repository.StudentRepository;
import java.util.List;

/**
 *
 * @author NandomPC
 */
public class StudentServiceImpl implements StudentService {

    private StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Student> getAllStudents() {
        return repository.getAllStudents();
    }

}
