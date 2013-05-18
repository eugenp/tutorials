package org.baeldung.spring.persistence.dao;

import java.util.List;

import org.baeldung.spring.persistence.model.Foo;

public interface IFooDao {

    Foo findOne(long id);

    List<Foo> findAll();

    void create(Foo entity);

    Foo update(Foo entity);

    void delete(Foo entity);

    void deleteById(long entityId);

}
