package com.baeldung.spring.hibernate;

import java.util.List;

public interface IFooDao {
    Foo findOne(long id);

    List<Foo> findAll();

    Foo update(Foo entity);

    void delete(Foo entity);

    void deleteById(long entityId);
}
