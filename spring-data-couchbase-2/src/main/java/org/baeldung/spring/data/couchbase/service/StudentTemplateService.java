package org.baeldung.spring.data.couchbase.service;

import java.util.List;

import org.baeldung.spring.data.couchbase.model.Student;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.view.ViewQuery;

@Service
@Qualifier("StudentTemplateService")
public class StudentTemplateService implements StudentService {

    private static final String DESIGN_DOC = "student";

    private CouchbaseTemplate template;

    @Autowired
    public void setCouchbaseTemplate(CouchbaseTemplate template) {
        this.template = template;
    }

    public Student findOne(String id) {
        return template.findById(id, Student.class);
    }

    public List<Student> findAll() {
        return template.findByView(ViewQuery.from(DESIGN_DOC, "all"), Student.class);
    }

    public List<Student> findByFirstName(String firstName) {
        return template.findByView(ViewQuery.from(DESIGN_DOC, "byFirstName"), Student.class);
    }

    public List<Student> findByLastName(String lastName) {
        return template.findByView(ViewQuery.from(DESIGN_DOC, "byLastName"), Student.class);
    }

    public void create(Student student) {
        student.setCreated(DateTime.now());
        template.insert(student);
    }

    public void update(Student student) {
        student.setUpdated(DateTime.now());
        template.update(student);
    }

    public void delete(Student student) {
        template.remove(student);
    }
}
