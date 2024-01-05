package com.baeldung.spring.insertableonly.entitymanager;

public interface PersistableEntityManagerBookRepository {
    <S extends EntityManagerBook> S persistBook(S entity);
}
