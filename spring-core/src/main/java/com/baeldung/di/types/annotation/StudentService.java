package com.baeldung.di.types.annotation;

import java.util.Arrays;
import java.util.List;

public class StudentService {

    public Student findOne() {
        return new Student(1, "Paul", 18);
    }

    public List<Student> findAll() {
        return Arrays.asList(
                new Student(1, "Paul", 18),
                new Student(2, "Ben", 17),
                new Student(3, "Pierre", 16),
                new Student(4, "Sophie", 17));
    }

    @Override
    public String toString() {
        return "StudentService{}";
    }
}
