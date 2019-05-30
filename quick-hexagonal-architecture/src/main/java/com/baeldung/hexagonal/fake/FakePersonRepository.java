package com.baeldung.hexagonal.fake;

import com.baeldung.hexagonal.domain.Person;
import com.baeldung.hexagonal.persistence.PersonRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;

class FakePersonRepository implements PersonRepository {

    private final List<Person> persons = new CopyOnWriteArrayList<>();

    @Override
    public Person getById(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("id must be greater than or equal to zero");
        }
        for (Person person : persons) {
            if (person.getId() == id) {
                return person;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public void save(Person person) {
        persons.add(person);
    }
}
