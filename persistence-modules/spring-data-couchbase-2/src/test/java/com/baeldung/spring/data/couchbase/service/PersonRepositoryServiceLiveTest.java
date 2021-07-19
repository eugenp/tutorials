package com.baeldung.spring.data.couchbase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PersonRepositoryServiceLiveTest extends PersonServiceLiveTest {

    @Autowired
    @Qualifier("PersonRepositoryService")
    public void setPersonService(PersonService service) {
        this.personService = service;
    }
}
