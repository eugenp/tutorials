package com.baeldung.hexagonal.usecases;

import com.baeldung.hexagonal.domain.Student;
import com.baeldung.hexagonal.inboundports.StudentService;
import com.baeldung.hexagonal.outboundports.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    @Override
    public Student getStudentById(int id) {
        return studentRepository.getStudentById(id);
    }

    @Override
    public void createStudent(Student student) {
        studentRepository.createStudent(student);
    }

    @Override
    public boolean deleteStudent(Student student) {
        return studentRepository.deleteStudent(student);
    }
}
