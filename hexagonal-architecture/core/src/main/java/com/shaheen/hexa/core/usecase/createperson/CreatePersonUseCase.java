package com.shaheen.hexa.core.usecase.createperson;

@FunctionalInterface
public interface CreatePersonUseCase {
    Long createPerson(CreatePersonCommand command);
}
