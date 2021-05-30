package com.shaheen.hexa.core.usecase;


import com.shaheen.hexa.core.domain.Person;

@FunctionalInterface
public interface CreatePersonPort {
    Long createPerson(Person person);
}
