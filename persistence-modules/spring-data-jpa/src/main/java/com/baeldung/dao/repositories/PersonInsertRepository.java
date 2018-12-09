package com.baeldung.dao.repositories;

import com.baeldung.domain.Person;

public interface PersonInsertRepository {
    void insert(Person person);
}
