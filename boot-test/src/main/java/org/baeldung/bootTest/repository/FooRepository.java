package org.baeldung.bootTest.repository;

import org.baeldung.bootTest.model.Foo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FooRepository extends JpaRepository<Foo, Long> {

    public Foo findOneByName(String name);
}
