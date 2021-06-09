package com.baeldung.architecture.service.impl;

import com.baeldung.architecture.model.StudentDto;
import com.baeldung.architecture.service.ServicePersistenceStudent;
import com.baeldung.architecture.service.ServiceStudent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceStudentImpl implements ServiceStudent {
    private ServicePersistenceStudent servicePersistenceStudent;

    @Override
    public List<StudentDto> findStudents() {
        return servicePersistenceStudent.findAllStudents();
    }

    @Override
    public StudentDto addStudent(StudentDto studentDto) {
        return servicePersistenceStudent.addStudent(studentDto);
    }
}
