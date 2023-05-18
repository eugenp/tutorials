package com.baeldung.spring.data.couchbase.service;

import static org.springframework.data.couchbase.core.query.QueryCriteria.where;

import java.util.List;
import java.util.Optional;

import com.baeldung.spring.data.couchbase.model.Student;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

@Service
@Qualifier("StudentTemplateService")
public class StudentTemplateService implements StudentService {

    private static final String DESIGN_DOC = "student";

    private CouchbaseTemplate template;

    @Autowired
    public void setCouchbaseTemplate(CouchbaseTemplate template) {
        this.template = template;
    }

    public Optional<Student> findOne(String id) {
        return Optional.of(template.findById(Student.class).one(id));
    }

    public List<Student> findAll() {
        return template.findByQuery(Student.class).all();
    }

    public List<Student> findByFirstName(String firstName) {
        return template.findByQuery(Student.class).matching(where("firstName").is(firstName)).all();
    }

    public List<Student> findByLastName(String lastName) {
        return template.findByQuery(Student.class).matching(where("lastName").is(lastName)).all();
    }

    public void create(Student student) {
        student.setCreated(DateTime.now());
        template.insertById(Student.class).one(student);
    }

    public void update(Student student) {
        student.setUpdated(DateTime.now());
        template.upsertById(Student.class).one(student);
    }

    public void delete(Student student) {
        template.removeById(Student.class).oneEntity(student);
    }
}
