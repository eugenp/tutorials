package com.baeldung.hibernate.scalarmethod;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;

public class HibernateScalarExample {

    private Session session;

    public HibernateScalarExample(Session session) {
        this.session = session;
    }

    public List<Object[]> fetchColumnWithNativeQuery() {
        return session.createNativeQuery("SELECT * FROM Student student")
          .list();
    }

    public List<Object[]> fetchColumnWithScalar() {
        return session.createNativeQuery("SELECT * FROM Student student")
          .addScalar("studentId", StandardBasicTypes.LONG)
          .addScalar("name", StandardBasicTypes.STRING)
          .addScalar("age", StandardBasicTypes.INTEGER)
          .list();
    }

    public List<String> fetchLimitedColumnWithScalar() {
        return session.createNativeQuery("SELECT * FROM Student student")
          .addScalar("name", StandardBasicTypes.STRING)
          .list();
    }

    public List<Object[]> fetchColumnWithOverloadedScalar() {
        return session.createNativeQuery("SELECT * FROM Student student")
          .addScalar("name", StandardBasicTypes.STRING)
          .addScalar("age")
          .list();
    }

    public Integer fetchAvgAgeWithScalar() {
        return (Integer) session.createNativeQuery("SELECT AVG(age) as avgAge FROM Student student")
          .addScalar("avgAge")
          .uniqueResult();
    }
}
