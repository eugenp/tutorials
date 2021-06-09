package com.baeldung.architecture.service.impl;

import com.baeldung.architecture.database.Student;
import com.baeldung.architecture.database.StudentRepository;
import com.baeldung.architecture.database.converter.StudentConverter;
import com.baeldung.architecture.model.StudentDto;
import com.baeldung.architecture.service.ServicePersistenceStudent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServicePersistenceStudentImpl implements ServicePersistenceStudent {
    private StudentRepository studentRepository;

    public ServicePersistenceStudentImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDto> findAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(StudentConverter::map)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto addStudent(StudentDto studentDto) {
        Student student = studentRepository.save(StudentConverter.map(studentDto));
        return StudentConverter.map(student);
    }
}
