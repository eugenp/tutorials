package com.baeldung.hexagonaljava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.hexagonaljava.entity.Student;
import com.baeldung.hexagonaljava.repository.StudentResultRepo;

@Component
public class StudentResultServiceImpl implements StudentResultService {

    @Autowired
    private StudentResultRepo studentResultRepo;

    @Override
    public void save(Student student) {
        studentResultRepo.save(student);
    }

    @Override
    public Double getTotalMarks(Integer id) {
        Student student = studentResultRepo.getStudent(id);
        double totalMarks = 0;
        for (double marks : student.getMarks()
          .values()) {
            totalMarks += marks;
        }
        return totalMarks;
    }
}
