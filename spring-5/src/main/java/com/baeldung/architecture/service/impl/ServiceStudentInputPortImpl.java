package com.baeldung.architecture.service.impl;

import com.baeldung.architecture.model.StudentDto;
import com.baeldung.architecture.service.ServiceStudentInputPort;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ServiceStudentInputPortImpl implements ServiceStudentInputPort {

    @Override
    public StudentDto addStudent(StudentDto studentDto) {
        // validate input
        if (StringUtils.isBlank(studentDto.getName())) {
            throw new RuntimeException("Student not valid");
        }

        return studentDto;
    }
}
