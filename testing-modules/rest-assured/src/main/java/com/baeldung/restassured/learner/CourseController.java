package com.baeldung.restassured.learner;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping(path = "/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public Collection<Course> getCourses() {
        return courseService.getCourses();
    }

    @GetMapping(path = "/{code}", produces = APPLICATION_JSON_UTF8_VALUE)
    public Course getCourse(@PathVariable String code) {
        return courseService.getCourse(code);
    }
}
