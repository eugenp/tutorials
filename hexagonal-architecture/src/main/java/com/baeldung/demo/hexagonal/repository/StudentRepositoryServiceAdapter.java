package com.baeldung.demo.hexagonal.repository;

import com.baeldung.demo.hexagonal.model.Student;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
public class StudentRepositoryServiceAdapter implements StudentRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void create(String name) {
        Student student = new Student();
        student.setName(name);

        entityManager.persist(student);
    }

    @Override
    public Student getStudent(Integer studentId) {
        return entityManager.find(Student.class, studentId);
    }
}
