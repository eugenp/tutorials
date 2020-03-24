package com.baeldung.springbootmvc.jsfapplication.model;

import java.util.Collection;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(int id);

    Collection<T> getAll();

    int save(T t);

    void update(T t);

    void delete(T t);
}