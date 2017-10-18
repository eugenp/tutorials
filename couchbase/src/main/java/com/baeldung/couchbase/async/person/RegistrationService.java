package com.baeldung.couchbase.async.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.couchbase.client.core.CouchbaseException;

@Service
public class RegistrationService {

    @Autowired
    private PersonCrudService crud;

    public void registerNewPerson(String name, String homeTown) {
        Person person = new Person();
        person.setName(name);
        person.setHomeTown(homeTown);
        crud.create(person);
    }

    public Person findRegistrant(String id) {
        try {
            return crud.read(id);
        } catch (CouchbaseException e) {
            return crud.readFromReplica(id);
        }
    }
}
