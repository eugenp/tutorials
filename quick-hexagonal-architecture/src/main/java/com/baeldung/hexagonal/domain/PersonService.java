package com.baeldung.hexagonal.domain;

public interface PersonService {

    Person create(CreatePersonRequest request);

    Person get(int id);
}
