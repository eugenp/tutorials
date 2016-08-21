package org.baeldung.persistence.dao;

import java.util.List;

import org.baeldung.persistence.model.Foo;

public interface IFooDao {
    List<Foo> findAll();
}
