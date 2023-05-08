package com.baeldung.spring.data.couchbase.service;

import static org.springframework.data.couchbase.core.query.QueryCriteria.where;

import java.util.List;

import com.baeldung.spring.data.couchbase.model.Person;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.query.Query;
import org.springframework.data.couchbase.core.query.QueryCriteria;
import org.springframework.stereotype.Service;

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
        return template.findById(Person.class).one(id);
    }

    public List<Person> findAll() {
        return template.findByQuery(Person.class).all();
    }

    public List<Person> findByFirstName(String firstName) {
        return template.findByQuery(Person.class).matching(where("firstName").is(firstName)).all();
    }

    public List<Person> findByLastName(String lastName) {
        return template.findByQuery(Person.class).matching(where("lastName").is(lastName)).all();
    }

    public void create(Person person) {
        person.setCreated(DateTime.now());
        template.insertById(Person.class).one(person);
    }

    public void update(Person person) {
        person.setUpdated(DateTime.now());
        template.removeById(Person.class).oneEntity(person);
    }

    public void delete(Person person) {
        template.removeById(Person.class).oneEntity(person);
    }
}
