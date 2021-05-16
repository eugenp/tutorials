package com.baeldung.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.baeldung.model.Foo;

public interface FooRepository extends JpaRepository<Foo, Long>, JpaSpecificationExecutor<Foo> {

}
