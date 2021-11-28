package com.baeldung.hexagonalarch.service.impl;

import com.baeldung.hexagonalarch.domain.model.Student;
import com.baeldung.hexagonalarch.repository.StudentRepository;
import com.baeldung.hexagonalarch.service.StudentService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
      this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
      return studentRepository.save(student);
    }

    @Override
    public Student getStudent(Long studentId) {
      Optional<Student> student = studentRepository.findByStudentId(studentId);

      if(student.isPresent()) {
        return student.get();
      }
      else {
        // Throw an exception...
        throw new RuntimeException("No Student Found.");
      }
    }

    @Override
    public List<Student> getAllStudents() {
      return studentRepository.findAll();
    }
}
