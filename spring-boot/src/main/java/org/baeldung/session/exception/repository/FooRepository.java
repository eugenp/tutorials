package org.baeldung.session.exception.repository;

import org.baeldung.boot.model.Foo;

public interface FooRepository {

    public void save(Foo foo);

    public Foo get(Integer id);
}
