package com.baeldung.adaptor;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baeldung.model.Student;
import com.baeldung.port.StudentDBPort;

@Repository
public class StudentServiceAdapter implements StudentDBPort {
    private EntityManager entityManager;

    @Autowired
    public StudentServiceAdapter(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    @Override
    public List<Student> findAll() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<Student> theQuery = currentSession.createQuery("from Student", Student.class);
        List<Student> students = theQuery.getResultList();
        return students;
    }

    @Override
    public void save(Student student) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(student);

    }
}
