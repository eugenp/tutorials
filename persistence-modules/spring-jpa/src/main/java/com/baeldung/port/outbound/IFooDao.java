package com.baeldung.port.outbound;

import java.util.List;

import com.baeldung.port.model.Foo;

public interface IFooDao {

    Foo findOne(long id);

    List<Foo> findAll();

    void create(Foo entity);

    Foo update(Foo entity);

    void delete(Foo entity);

    void deleteById(long entityId);

}
