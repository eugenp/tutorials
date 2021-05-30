package com.shaheen.hexa.core.usecase.getperson;

import com.shaheen.hexa.core.domain.Person;

@FunctionalInterface
public interface GetPersonUseCase {
    Person getPerson(Long id);
}
