package com.shaheen.hexa.core.usecase.getperson;

import com.shaheen.hexa.core.domain.Person;

@FunctionalInterface
public interface GetPersonPort {
    Person getPerson(Long id);
}
