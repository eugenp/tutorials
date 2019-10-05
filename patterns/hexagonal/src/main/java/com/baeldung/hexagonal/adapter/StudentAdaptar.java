package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.domain.dto.Student;
import com.baeldung.hexagonal.domain.port.IDataPort;
import com.baeldung.hexagonal.domain.port.IStudentPort;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StudentAdaptar implements IStudentPort {

    @Autowired
    private IDataPort dataPort;

    @Override
    public List<Student> getStudents() {
        return dataPort.getStudentsData();
    }
}
