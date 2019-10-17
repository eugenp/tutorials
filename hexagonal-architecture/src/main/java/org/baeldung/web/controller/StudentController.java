package org.baeldung.web.controller;

import org.baeldung.persistence.entity.Student;
import org.baeldung.persistence.repository.StudentRepository;
import org.baeldung.service.ports.IStudentDb;
import org.baeldung.service.ports.IStudentUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "student")
public class StudentController implements IStudentUI {

    @Autowired
    private IStudentDb iStudentDb;
    @Override
    public Student create(Student student) {
        return iStudentDb.create(student);
    }
    @Override
    public Optional<Student> view( Long id) {
        return iStudentDb.findStudentById(id);
    }
}
