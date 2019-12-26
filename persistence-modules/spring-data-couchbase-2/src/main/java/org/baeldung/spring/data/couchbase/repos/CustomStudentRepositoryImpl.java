package org.baeldung.spring.data.couchbase.repos;

import java.util.List;

import org.baeldung.spring.data.couchbase.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;

import com.couchbase.client.java.view.Stale;
import com.couchbase.client.java.view.ViewQuery;

public class CustomStudentRepositoryImpl implements CustomStudentRepository {

    private static final String DESIGN_DOC = "student";

    @Autowired
    private CouchbaseTemplate template;

    public List<Student> findByFirstNameStartsWith(String s) {
        return template.findByView(ViewQuery.from(DESIGN_DOC, "byFirstName").startKey(s).stale(Stale.FALSE), Student.class);
    }
}
