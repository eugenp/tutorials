package org.baeldung.session.exception.repository;

import org.baeldung.boot.model.Foo;

public interface FooRepository {

    void save(Foo foo);

    Foo get(Integer id);
}
