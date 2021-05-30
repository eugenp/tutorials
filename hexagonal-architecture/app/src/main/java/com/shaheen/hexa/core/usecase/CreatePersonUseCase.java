package com.shaheen.hexa.core.usecase;

@FunctionalInterface
public interface CreatePersonUseCase {
    Long createPerson(CreatePersonCommand command);
}
