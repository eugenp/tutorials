package org.baeldung.spring.persistence.dao;

import java.util.List;

import org.baeldung.spring.persistence.model.Foo;

public interface IFooDao {

    Foo findOne(final Long id);

    List<Foo> findAll();

    void create(final Foo entity);

    Foo update(final Foo entity);

    void delete(final Foo entity);

    void deleteById(final Long entityId);

}
