package com.baeldung.hexagonal.domain;

/**
 * Public interface for making requests to the application. Adapters which wish to work with the application use this interface.
 */
public interface PersonService {

    Person create(CreatePersonRequest request);

    Person get(int id);
}
