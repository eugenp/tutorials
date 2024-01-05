package com.baeldung.spring.insertableonly.entitymanager;

public interface PersistableEntityManagerBookRepository {
    <S extends EntityManagerBook> S persist(S entity);
}
