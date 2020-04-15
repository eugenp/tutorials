package com.baeldung.jpa.text;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
