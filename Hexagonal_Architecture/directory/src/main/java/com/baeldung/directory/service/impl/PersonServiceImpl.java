package com.baeldung.directory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.directory.model.Person;
import com.baeldung.directory.repository.PersonRepository;
import com.baeldung.directory.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository repository;

    public List<Person> getAllPersons() {
        return repository.findAll();
    }

    public Person getPersonWithName(String name) {
        return repository.findByName(name);
    }

    public String addPerson(Person person) {
        String result = "Success";
        try {
            repository.save(person);
        } catch (Exception exc) {
            result = "Failure";
            exc.printStackTrace();
        }
        return result;
    }

}
