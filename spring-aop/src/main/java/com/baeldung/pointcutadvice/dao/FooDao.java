package com.baeldung.pointcutadvice.dao;

import com.baeldung.pointcutadvice.Foo;
import com.baeldung.pointcutadvice.annotations.Loggable;
import org.springframework.stereotype.Repository;

@Repository
public class FooDao {

    public String findById(Long id) {
        return "Bazz";
    }

    @Loggable
    public Foo create(Long id, String name) {
        return new Foo(id, name);
    }

    public Foo merge(Foo foo) {
        return foo;
    }
}
