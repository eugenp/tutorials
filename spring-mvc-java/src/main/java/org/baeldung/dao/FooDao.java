package org.baeldung.dao;

import org.baeldung.model.Foo;
import org.springframework.stereotype.Repository;

@Repository
public class FooDao {

    public String findById(Long id) {
        return "Bazz";
    }

    public Foo create(Long id, String name) {
        return new Foo(id, name);
    }
}
