package com.shaheen.hexa.core.usecase.createperson;

import com.shaheen.hexa.core.domain.Person;

@FunctionalInterface
public interface CreatePersonPort {
    Long createPerson(Person person);
}
