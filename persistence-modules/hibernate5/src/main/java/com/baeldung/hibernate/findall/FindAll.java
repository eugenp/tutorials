package com.baeldung.hibernate.findall;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;

import com.baeldung.hibernate.pojo.Student;

public class FindAll {

    private Session session;

    public FindAll(Session session) {
        super();
        this.session = session;
    }

    public List<Student> findAllWithJpql() {
        return session.createQuery("SELECT a FROM Student a", Student.class).getResultList();
    }

    public List<Student> findAllWithCriteriaQuery() {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Student> cq = cb.createQuery(Student.class);
        Root<Student> rootEntry = cq.from(Student.class);
        CriteriaQuery<Student> all = cq.select(rootEntry);
        TypedQuery<Student> allQuery = session.createQuery(all);
        return allQuery.getResultList();
    }
}
