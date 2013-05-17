package org.baeldung.spring.persistence.dao;

import java.util.List;

import org.baeldung.spring.persistence.model.Foo;

public interface IFooDao {

    Foo findOne(Long id);

    List<Foo> findAll();

    void create(Foo entity);

    void update(Foo entity);

    void delete(Foo entity);

    void deleteById(Long entityId);

}
