package com.baeldung.jacksonlazyfields.dao;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.jacksonlazyfields.model.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {

    Set<Course> findAllByDepartmentId(Long id);
}
