package com.baeldung.hexagonal.persistence;

import com.baeldung.hexagonal.domain.CreatePersonRequest;
import com.baeldung.hexagonal.domain.Person;

public interface PersonFactory {

    Person create(CreatePersonRequest request);

}
