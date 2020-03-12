package com.baeldung.hexagonal.architecture.repository;

import com.baeldung.hexagonal.architecture.domain.Course;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.Assert.*;

public class CourseRepositoryImplTest {

    @Autowired
    private CourseRepository courseRepository = new CourseRepositoryImpl();


    @Test
    public void shoudFindByCourseCode_thenReturnCourse() {
        String code = "CS201";
        final Course course = createOrder(code);
        courseRepository.addCourse(course);

        final Optional<Course> courseByCode = courseRepository.findCourseByCode(code);

        assertEquals(course, courseByCode.get());
    }

    private Course createOrder(String code) {
        Course course = new Course();
        course.setName("SPRING IN ACTION");
        course.setCode(code);
        course.setCategory("ENTERPRISE PROGRAMMING");
        return course;
    }

}