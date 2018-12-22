package com.baeldung.dao.repositories;

import com.baeldung.domain.Person;

public interface PersonEntityManagerInsertRepository {
    void insert(Person person);
}
