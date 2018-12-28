package com.baeldung.dao.repositories;

import com.baeldung.domain.Person;

public interface PersonQueryInsertRepository {
    void insert(Person person);
}
