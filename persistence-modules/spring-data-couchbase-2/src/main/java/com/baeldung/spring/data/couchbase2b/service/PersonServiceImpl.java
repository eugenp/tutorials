package com.baeldung.spring.data.couchbase2b.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.baeldung.spring.data.couchbase2b.repos.PersonRepository;
import com.baeldung.spring.data.couchbase.model.Person;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonRepository repo;

    @Autowired
    public void setPersonRepository(PersonRepository repo) {
        this.repo = repo;
    }

    public Optional<Person> findOne(String id) {
        return repo.findById(id);
    }

    public List<Person> findAll() {
        List<Person> people = new ArrayList<>();
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
