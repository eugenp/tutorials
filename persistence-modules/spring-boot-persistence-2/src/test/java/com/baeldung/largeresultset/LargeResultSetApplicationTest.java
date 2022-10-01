package com.baeldung.largeresultset;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = LargeResultSetApplication.class)
class LargeResultSetTest {

    @Autowired
    private StudentRepository repository;

    @Autowired
    private StudentService service;

    @AfterEach
    public void afterEach() {
        repository.deleteAll();
    }

    @Test
    void processStudentsInSlices() {
        saveStudents(12);

        service.processStudentsByFirstName("john");
    }

    @Test
    void processStudentsInPages() {
        saveStudents(12);

        service.processStudentsByLastName("doe");
    }

    @Test
    void processStudentsByFirstNameUsingStreams() {
        saveStudents(12);

        service.processStudentsByFirstNameUsingStreams("john");
    }

    private void saveStudents(int count) {
        List<Student> students = IntStream.range(0, count).boxed()
            .map(i -> new Student("john", "doe"))
            .collect(Collectors.toList());
        repository.saveAll(students);
    }

}