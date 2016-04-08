package org.baeldung.spring.data.couchbase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PersonRepositoryServiceTest extends PersonServiceTest {

    @Autowired
    @Qualifier("PersonRepositoryService")
    public void setPersonService(PersonService service) {
        this.personService = service;
    }
}
