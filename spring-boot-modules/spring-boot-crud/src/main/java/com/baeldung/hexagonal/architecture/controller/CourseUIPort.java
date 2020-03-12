package com.baeldung.hexagonal.architecture.controller;

import com.baeldung.hexagonal.architecture.domain.Course;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CourseUIPort {

    @PostMapping
    void addCourse(@RequestBody Course course);

    @GetMapping("/{name}")
    Course findCourseByCode(@PathVariable String code) throws IllegalAccessException;

    @GetMapping
    List<Course> findAllCourses() ;
}
