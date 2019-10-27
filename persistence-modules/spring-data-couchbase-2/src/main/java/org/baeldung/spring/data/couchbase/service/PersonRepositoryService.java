package org.baeldung.spring.data.couchbase.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.baeldung.spring.data.couchbase.model.Person;
import org.baeldung.spring.data.couchbase.repos.PersonRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("PersonRepositoryService")
public class PersonRepositoryService implements PersonService {

    private PersonRepository repo;

    @Autowired
    public void setPersonRepository(PersonRepository repo) {
        this.repo = repo;
    }

    public Person findOne(String id) {
        return repo.findOne(id);
    }

    public List<Person> findAll() {
        List<Person> people = new ArrayList<Person>();
        Iterator<Person> it = repo.findAll().iterator();
        while (it.hasNext()) {
            people.add(it.next());
        }
        return people;
    }

    public List<Person> findByFirstName(String firstName) {
        return repo.findByFirstName(firstName);
    }

    public List<Person> findByLastName(String lastName) {
        return repo.findByLastName(lastName);
    }

    public void create(Person person) {
        person.setCreated(DateTime.now());
        repo.save(person);
    }

    public void update(Person person) {
        person.setUpdated(DateTime.now());
        repo.save(person);
    }

    public void delete(Person person) {
        repo.delete(person);
    }
}
