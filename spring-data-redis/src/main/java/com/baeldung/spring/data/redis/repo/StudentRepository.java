package com.baeldung.spring.data.redis.repo;

import com.baeldung.spring.data.redis.model.Student;

import java.util.Map;

interface StudentRepository {

    void saveStudent(Student person);

    void updateStudent(Student student);

    Student findStudent(String id);

    Map<Object, Object> findAllStudents();

    void deleteStudent(String id);
}
