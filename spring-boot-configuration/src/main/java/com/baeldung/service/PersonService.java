package com.baeldung.service;

import com.baeldung.domain.Person;
import com.baeldung.exception.PersonNotFoundException;
import com.baeldung.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public void add(Person person) {
        personRepository.save(person);
    }

    public void delete(long id) {
        personRepository.deleteById(id);
    }

    public List<Person> getPersons() {
        return (List<Person>) personRepository.findAll();
    }

    public Person getPersonById(long id) throws PersonNotFoundException {
        Optional<Person> optionalDog = personRepository.findById(id);
        return optionalDog.orElseThrow(() -> new PersonNotFoundException("Couldn't find a Person with id: " + id));
    }
}
