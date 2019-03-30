package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.adapters.InMemoryStudentRepositoryAdapter;
import com.baeldung.hexagonal.entity.Student;
import com.baeldung.hexagonal.ports.StudentRepositoryPort;

public class StudentServiceImpl implements StudentService {

    private StudentRepositoryPort studentRepository = new InMemoryStudentRepositoryAdapter();

    @Override
    public Student createStudent(Student student) {
        if(student.getAge() > 15) {
            student.setGrade("A");
        } else if (student.getAge() > 12 && student.getAge() <= 15) {
            student.setGrade("B");
        } else {
            student.setGrade("C");
        }
        return studentRepository.saveStudent(student);
    }

    @Override
    public Student retrieveStudentById(int id) {
        return studentRepository.getStudent(id);
    }

}
