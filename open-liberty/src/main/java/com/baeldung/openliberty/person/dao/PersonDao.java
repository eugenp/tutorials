package com.baeldung.openliberty.person.dao;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.baeldung.openliberty.person.model.Person;

@RequestScoped
public class PersonDao {

    @PersistenceContext(name = "jpa-unit")
    private EntityManager em;

    public Person createPerson(Person person) {
        em.persist(person);
        return person;
    }

    public Person readPerson(int personId) {
        return em.find(Person.class, personId);
    }

}