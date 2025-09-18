package com.baeldung.jacksonlazyfields.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.jacksonlazyfields.dao.StudentDto;
import com.baeldung.jacksonlazyfields.dao.StudentRepository;
import com.baeldung.jacksonlazyfields.model.Department;
import com.baeldung.jacksonlazyfields.model.Student;

@RestController
public class StudentController {

    private StudentRepository repository;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }

    /**
     * curl example: curl -X POST http://localhost:8080/students -H "Content-Type: application/json" -d "{\"name\":\"John Doe\"}"
     * 
     * Example including department (assuming department with id 1 exists):
     * curl -X POST http://localhost:8080/students -H "Content-Type: application/json" -d "{\"name\":\"John Doe\", \"department\":{\"id\":1}}"
     * @param student
     * @return Student
     */
    @PostMapping("/students")
    public Student postStudent(@RequestBody Student student) {
        return repository.save(student);
    }

    @PostMapping("/students-dto")
    public StudentDto create(@RequestBody StudentDto dto) {
        Student student = new Student();
        student.setName(dto.name());

        repository.save(student);

        return new StudentDto(1l, "", null);
    }

    /**
     * curl example: curl -X GET http://localhost:8080/students/1
     * @param id
     * @return Student
     */
    @GetMapping("/students/{id}")
    public Student getStudent(@PathVariable("id") Long id) {
        Optional<Student> optionalStudent = repository.findById(id);
        if (optionalStudent.isPresent()) {
            Student stu = optionalStudent.get();
            Department department = stu.getDepartment();
            if (department != null) {
                System.out.println(department.getName());
            }
            // System.out.println(stu.getCourses());
        }
        return optionalStudent
            .orElseThrow(IllegalArgumentException::new);
    }
}
