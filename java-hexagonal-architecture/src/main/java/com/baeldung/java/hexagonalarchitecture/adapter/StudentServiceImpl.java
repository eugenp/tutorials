package com.baeldung.java.hexagonalarchitecture.adapter;

import java.util.List;

import com.baeldung.java.hexagonalarchitecture.domain.Student;
import com.baeldung.java.hexagonalarchitecture.port.StudentRepository;
import com.baeldung.java.hexagonalarchitecture.port.StudentService;

public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository = new StudentRepositoryImpl();
    
    public void createStudentService(Student student) {
        studentRepository.createStudent(student);
    }

    public List<Student> listStudentService() {
        return studentRepository.listStudent();
    }

}
