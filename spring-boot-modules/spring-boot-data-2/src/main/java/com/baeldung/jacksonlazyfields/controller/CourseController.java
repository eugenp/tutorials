package com.baeldung.jacksonlazyfields.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.jacksonlazyfields.dao.CourseRepository;
import com.baeldung.jacksonlazyfields.model.Course;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private CourseRepository repository;

    public CourseController(CourseRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Course post(@RequestBody Course course) {
        return repository.save(course);
    }

    @GetMapping("/{id}")
    public Optional<Course> get(@PathVariable("id") Long id) {
        return repository.findById(id);
    }
}
