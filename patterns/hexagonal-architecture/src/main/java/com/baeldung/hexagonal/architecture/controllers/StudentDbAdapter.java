package com.baeldung.hexagonal.architecture.controllers;

import com.baeldung.hexagonal.architecture.exception.BadRequestException;
import com.baeldung.hexagonal.architecture.exception.NotFoundException;
import com.baeldung.hexagonal.architecture.models.Entity.StudentModel;
import com.baeldung.hexagonal.architecture.models.Student;
import com.baeldung.hexagonal.architecture.repository.StudentRepositoryPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service("studentDbAdapter")
public class StudentDbAdapter implements StudentPort {

    @Autowired
    @Qualifier("studentRepositoryDbAdapter")
    StudentRepositoryPort studentRepositoryPort;

    @Override
    public void registerStudent(Student registerRequest) throws BadRequestException {

        studentRepositoryPort.createStudentData(registerRequest.getName(),registerRequest.getEmail(),registerRequest.getBatch(),registerRequest.getRegistrationNumber());
    }

    @Override
    public Student viewRegistration(Integer registrationNumber) throws NotFoundException {

        StudentModel studentModel = studentRepositoryPort.getStudentData(registrationNumber);
        if(studentModel == null)
            throw new NotFoundException("No data found for student:",HttpStatus.NOT_FOUND);
        return  new Student(studentModel.getRegistration_Number(),studentModel.getName(),studentModel.getEmail(),studentModel.getBatch());
    }
}
