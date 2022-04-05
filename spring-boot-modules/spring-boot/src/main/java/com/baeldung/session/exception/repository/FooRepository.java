package com.baeldung.session.exception.repository;

import com.baeldung.demo.model.Foo;

public interface FooRepository {

    void save(Foo foo);

    Foo get(Integer id);
}
