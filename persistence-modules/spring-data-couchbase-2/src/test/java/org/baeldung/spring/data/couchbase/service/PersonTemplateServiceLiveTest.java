package org.baeldung.spring.data.couchbase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class PersonTemplateServiceLiveTest extends PersonServiceLiveTest {

    @Autowired
    @Qualifier("PersonTemplateService")
    public void setPersonService(PersonService service) {
        this.personService = service;
    }
}
