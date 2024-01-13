package com.baeldung.spring.insertableonly.entitymanager;

public interface PersistableEntityManagerBookRepository<S> {
    S persist(S entity);
}
