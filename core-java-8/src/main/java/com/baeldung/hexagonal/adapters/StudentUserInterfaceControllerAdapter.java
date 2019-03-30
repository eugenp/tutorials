package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.entity.Student;
import com.baeldung.hexagonal.ports.StudentUserInterfacePort;
import com.baeldung.hexagonal.service.StudentService;
import com.baeldung.hexagonal.service.StudentServiceImpl;

public class StudentUserInterfaceControllerAdapter implements
StudentUserInterfacePort {

    StudentService studentService = new StudentServiceImpl();

    @Override
    public Student createStudent(Student student) {
        return studentService.createStudent(student);
    }

    @Override
    public Student getStudent(int id) {
        return studentService.retrieveStudentById(id);
    }

}
