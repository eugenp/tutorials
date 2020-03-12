package com.baeldung.hexagonal.architecture.repository;

import com.baeldung.hexagonal.architecture.domain.Course;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class CourseRepositoryImpl implements CourseRepository {
    @Override
    public void addCourse(Course course) {
        courses.put(course.getCode(), course);
    }

    @Override
    public Optional<Course> findCourseByCode(String courseCode) {
        return Optional.of(courses.get(courseCode));
    }

    @Override
    public List<Course> findAll() {
        return courses.values().stream().collect(Collectors.toList());
    }

    Map<String, Course> courses = new HashMap<>();


    private void init() {

        final Course course1 = new Course();
        course1.setCode("CS101");
        course1.setCategory("LANGUAGES");
        course1.setName("JAVA FOR DUMMIES");
        courses.put("CS101", course1);

        final Course course2 = new Course();
        course2.setCode("CS102");
        course2.setCategory("LANGUAGES");
        course2.setName("ADVANCED JAVA");
        courses.put("CS102", course2);

    }
}
