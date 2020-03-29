package com.baeldung.adaptor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.model.Student;
import com.baeldung.port.StudentRESTPort;
import com.baeldung.service.StudentService;
@RestController
@RequestMapping("/student")
public class StudentControllerAdapter implements StudentRESTPort {
    @Autowired
    private StudentService studentService;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public Student registerStudent(@RequestBody Student student) {
        studentService.save(student);
        return student;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/viewAll")
    public List<Student> getAllStudents() {
        return studentService.findAll();
    }

}

