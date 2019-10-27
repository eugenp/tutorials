package org.baeldung.session.exception.repository;

import org.baeldung.demo.model.Foo;

public interface FooRepository {

    void save(Foo foo);

    Foo get(Integer id);
}
