package com.baeldung.hibernate.scalarmethod;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;

import com.baeldung.hibernate.pojo.Student;

public class ScalarDemo {

    private Session session;

    public ScalarDemo(Session session) {
        super();
        this.session = session;
    }

    public List<Student> fetchColumnWithNativeQuery() {
        return session.createNativeQuery("SELECT * FROM Student student")
            .list();
    }

    public List<Student> fetchColumnWithScalar() {
        return session.createNativeQuery("SELECT * FROM Student student")
            .addScalar("name", StandardBasicTypes.STRING)
            .list();
    }

    public List<Student> fetchWithOverloadedScalar() {
        return session.createNativeQuery("SELECT * FROM Student student")
            .addScalar("name", StandardBasicTypes.STRING)
            .addScalar("age")
            .list();
    }
}
