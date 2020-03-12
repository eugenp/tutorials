package com.baeldung.hexagonal.architecture.controller;

import com.baeldung.hexagonal.architecture.domain.Course;
import com.baeldung.hexagonal.architecture.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController implements CourseUIPort{

    @Autowired
    private CourseService courseService;

    @Override
    public void addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
    }

    @Override
    public Course findCourseByCode(@PathVariable String code) throws IllegalAccessException {
        return courseService.findCourseByCode(code).orElseThrow(IllegalAccessException::new);
    }

    @Override
    public List<Course> findAllCourses() {
        return courseService.findAll();
    }
}
