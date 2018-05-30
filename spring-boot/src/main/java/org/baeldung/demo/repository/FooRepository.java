package org.baeldung.demo.repository;

import org.baeldung.demo.model.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FooRepository extends JpaRepository<Foo, Integer> {
    public Foo findByName(String name);
}
