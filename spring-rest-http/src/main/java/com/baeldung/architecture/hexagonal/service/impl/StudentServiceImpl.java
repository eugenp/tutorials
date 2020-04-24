package com.baeldung.architecture.hexagonal.service.impl;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baeldung.architecture.hexagonal.dao.StudentRepository;
import com.baeldung.architecture.hexagonal.dao.model.Student;
import com.baeldung.architecture.hexagonal.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepo;

    public void add(Student student) {
        studentRepo.add(student);
    }

    public void delete(Student student) {
        studentRepo.delete(student);
    }

    public Map<Integer, Student> getAll() {
        return studentRepo.getAll();
    }
}
