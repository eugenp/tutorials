package com.baeldung.largeresultset.service;

import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.largeresultset.Student;
import com.baeldung.largeresultset.StudentRepository;

@Service
public class StudentService {
    private static final int BATCH_SIZE = 5;
    private final StudentRepository repository;
    private final EmailService emailService;
    private final EntityManager entityManager;

    public StudentService(StudentRepository repository, EmailService emailService, EntityManager entityManager) {
        this.repository = repository;
        this.emailService = emailService;
        this.entityManager = entityManager;
    }

    public void processStudentsByFirstName(String firstName) {
        Slice<Student> slice = repository.findAllByFirstName(firstName, PageRequest.of(0, BATCH_SIZE));
        List<Student> studentsInBatch = slice.getContent();
        studentsInBatch.forEach(emailService::sendEmailToStudent);

        while (slice.hasNext()) {
            slice = repository.findAllByFirstName(firstName, slice.nextPageable());
            slice.getContent()
                .forEach(emailService::sendEmailToStudent);
        }
    }

    public void processStudentsByLastName(String lastName) {
        Page<Student> page = repository.findAllByLastName(lastName, PageRequest.of(0, BATCH_SIZE));
        page.getContent()
            .forEach(emailService::sendEmailToStudent);

        while (page.hasNext()) {
            page = repository.findAllByLastName(lastName, page.nextPageable());
            page.getContent()
                .forEach(emailService::sendEmailToStudent);
        }
    }

    @Transactional(readOnly = true)
    public void processStudentsByFirstNameUsingStreams(String firstName) {
        try (Stream<Student> students = repository.findAllByFirstName(firstName)) {
            students.peek(entityManager::detach)
                .forEach(emailService::sendEmailToStudent);
        }
    }

}