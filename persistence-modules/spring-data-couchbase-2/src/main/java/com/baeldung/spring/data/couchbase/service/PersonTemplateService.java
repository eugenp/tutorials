package com.baeldung.spring.data.couchbase.service;

import java.util.List;

import com.baeldung.spring.data.couchbase.model.Person;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.view.ViewQuery;

@Service
@Qualifier("PersonTemplateService")
public class PersonTemplateService implements PersonService {

    private static final String DESIGN_DOC = "person";

    private CouchbaseTemplate template;

    @Autowired
    public void setCouchbaseTemplate(CouchbaseTemplate template) {
        this.template = template;
    }

    public Person findOne(String id) {
        return template.findById(id, Person.class);
    }

    public List<Person> findAll() {
        return template.findByView(ViewQuery.from(DESIGN_DOC, "all"), Person.class);
    }

    public List<Person> findByFirstName(String firstName) {
        return template.findByView(ViewQuery.from(DESIGN_DOC, "byFirstName"), Person.class);
    }

    public List<Person> findByLastName(String lastName) {
        return template.findByView(ViewQuery.from(DESIGN_DOC, "byLastName"), Person.class);
    }

    public void create(Person person) {
        person.setCreated(DateTime.now());
        template.insert(person);
    }

    public void update(Person person) {
        person.setUpdated(DateTime.now());
        template.update(person);
    }

    public void delete(Person person) {
        template.remove(person);
    }
}
