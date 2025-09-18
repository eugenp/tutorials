package com.baeldung.jacksonlazyfields.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.jacksonlazyfields.dao.DepartmentRepository;
import com.baeldung.jacksonlazyfields.dao.StudentRepository;
import com.baeldung.jacksonlazyfields.model.Department;
import com.baeldung.jacksonlazyfields.model.Student;

@RestController
public class DepartmentController {

    private DepartmentRepository repository;
    private StudentRepository studentRepository;

    public DepartmentController(DepartmentRepository repository, StudentRepository studentRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
    }

    /**
     * curl example: curl -X GET http://localhost:8080/departments/1
     * @param id
     * @return Department
     */
    @GetMapping("/departments/{id}")
    public Department getDepartment(@PathVariable("id") Long id) {
        return repository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * curl example: curl -X POST http://localhost:8080/departments -H "Content-Type: application/json" -d "{\"name\":\"Computer Science\"}"
     * @param department
     * @return Department
     */
    @PostMapping("/departments")
    public Department postDepartment(@RequestBody Department department) {
        Department save = repository.save(department);

        List<Student> students = new ArrayList<>();
        if (department.getStudents() != null) {
            for (Student student : department.getStudents()) {
                students.add(studentRepository.save(student));
            }
            department.setStudents(students);
        }
        return repository.save(save);
    }
}
