package com.baeldung.hexagonal.architecture.repository;

import com.baeldung.hexagonal.architecture.domain.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    void addCourse(Course course);

    Optional<Course> findCourseByCode(String courseCode);

    List<Course> findAll();
}
