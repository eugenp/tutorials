package com.baeldung.jpa.text;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ExamRepository {

    private EntityManagerFactory emf = null;

    public ExamRepository() {
        emf = Persistence.createEntityManagerFactory("jpa-h2-text");
    }

    public Exam find(Long id) {
        EntityManager entityManager = emf.createEntityManager();
        Exam exam = entityManager.find(Exam.class, id);
        entityManager.close();
        return exam;
    }

    public Exam save(Exam exam) {

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction()
            .begin();
        exam = entityManager.merge(exam);
        entityManager.getTransaction()
            .commit();
        entityManager.close();

        return exam;
    }

    public void clean() {
        emf.close();
    }

}
