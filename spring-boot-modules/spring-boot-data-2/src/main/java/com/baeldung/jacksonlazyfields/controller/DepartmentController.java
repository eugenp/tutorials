package com.baeldung.jacksonlazyfields.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.jacksonlazyfields.dao.CourseRepository;
import com.baeldung.jacksonlazyfields.dao.DepartmentRepository;
import com.baeldung.jacksonlazyfields.dto.DepartmentDto;
import com.baeldung.jacksonlazyfields.model.Course;
import com.baeldung.jacksonlazyfields.model.Department;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private DepartmentRepository repository;
    private CourseRepository courseRepository;

    public DepartmentController(DepartmentRepository repository, CourseRepository courseRepository) {
        this.repository = repository;
        this.courseRepository = courseRepository;
    }

    @PostMapping
    public Department post(@RequestBody Department department) {
        return repository.save(department);
    }

    @GetMapping("/{id}")
    public Optional<Department> get(@PathVariable("id") Long id) {
        return repository.findById(id);
    }

    @PostMapping("/{id}/course")
    public DepartmentDto postCourse(@PathVariable("id") Long id, @RequestBody Course course) {
        Department department = repository.findById(id)
            .orElseThrow();
        course.setDepartment(department);
        courseRepository.save(course);

        List<String> allDepartmentCourses = courseRepository.findAllByDepartmentId(id)
            .stream()
            .map(Course::getName)
            .toList();
        return new DepartmentDto(department.getId(), department.getName(), allDepartmentCourses);
    }
}
