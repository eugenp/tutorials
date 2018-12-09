package com.baeldung.dao.repositories;

public interface InsertRepository<T> {
    <S extends T> void insert(S entity);
}
