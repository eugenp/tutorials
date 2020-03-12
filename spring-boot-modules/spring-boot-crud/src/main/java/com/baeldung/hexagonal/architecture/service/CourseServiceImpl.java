package com.baeldung.hexagonal.architecture.service;

import com.baeldung.hexagonal.architecture.domain.Course;
import com.baeldung.hexagonal.architecture.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void addCourse(Course course) {
        courseRepository.addCourse(course);
    }

    @Override
    public Optional<Course> findCourseByCode(String courseCode) {
        return courseRepository.findCourseByCode(courseCode);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }
}
