package org.baeldung.boot.repository;

import org.baeldung.boot.model.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FooRepository extends JpaRepository<Foo, Integer> {
    public Foo findByName(String name);
}
