package com.baeldung.architecture.service.impl;

import com.baeldung.architecture.model.StudentDto;
import com.baeldung.architecture.service.ServiceStudentBusiness;
import com.baeldung.architecture.service.ServiceStudentInputPort;
import com.baeldung.architecture.service.ServiceStudentOutputPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServiceStudentBusinessImpl implements ServiceStudentBusiness {
    private ServiceStudentInputPort serviceStudentInputPort;
    private ServiceStudentOutputPort serviceStudentOutputPort;

    public ServiceStudentBusinessImpl(ServiceStudentInputPort serviceStudentInputPort, ServiceStudentOutputPort serviceStudentOutputPort) {
        this.serviceStudentInputPort = serviceStudentInputPort;
        this.serviceStudentOutputPort = serviceStudentOutputPort;
    }

    @Override
    public List<StudentDto> findStudents() {
        List<StudentDto> allStudents = serviceStudentOutputPort.findAllStudents();
        // do your business logic
        return allStudents;
    }

    @Override
    public StudentDto addStudent(StudentDto studentDto) {
        StudentDto studentValidated = serviceStudentInputPort.addStudent(studentDto);
        // do your business logic
        return serviceStudentOutputPort.addStudent(studentValidated);
    }
}
