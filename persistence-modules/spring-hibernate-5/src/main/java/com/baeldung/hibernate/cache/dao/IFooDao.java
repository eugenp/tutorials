package com.baeldung.hibernate.cache.dao;

import com.baeldung.hibernate.cache.model.Foo;

import java.util.List;

public interface IFooDao {

    Foo findOne(long id);

    List<Foo> findAll();

    void create(Foo entity);

    Foo update(Foo entity);

    void delete(Foo entity);

    void deleteById(long entityId);

    long countAllRowsUsingHibernateCriteria();

    long getFooCountByBarNameUsingHibernateCriteria(String barName);

    long getFooCountByBarNameAndFooNameUsingHibernateCriteria(String barName, String fooName);
}
