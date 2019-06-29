package com.baeldung.hexagonal.architecture.port.infrastructure;

import com.baeldung.hexagonal.architecture.domain.Person;

public interface PersonInfrastructurePort {

    void addPerson(Person person);

    Person getPerson(int personId);
}
