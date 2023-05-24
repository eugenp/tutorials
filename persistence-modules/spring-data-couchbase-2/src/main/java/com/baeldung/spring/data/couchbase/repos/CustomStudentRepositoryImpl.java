package com.baeldung.spring.data.couchbase.repos;

import java.util.List;

import com.baeldung.spring.data.couchbase.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.query.QueryCriteria;

public class CustomStudentRepositoryImpl implements CustomStudentRepository {

    private static final String DESIGN_DOC = "student";

    @Autowired
    private CouchbaseTemplate template;

    public List<Student> findByFirstNameStartsWith(String s) {
        return template.findByQuery(Student.class).matching(QueryCriteria.where("firstName").startingWith(s)).all();
    }
}
