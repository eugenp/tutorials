package com.baeldung.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.model.Foo;

public interface FooRepository extends JpaRepository<Foo, Long> {
}
