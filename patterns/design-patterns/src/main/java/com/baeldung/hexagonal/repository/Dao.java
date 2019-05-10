package com.baeldung.hexagonal.repository;

import java.util.List;

public interface Dao<T> {

    List<T> getAll();

    T save(T t);

    T update(T t, String[] params);

    boolean delete(T t);
}
