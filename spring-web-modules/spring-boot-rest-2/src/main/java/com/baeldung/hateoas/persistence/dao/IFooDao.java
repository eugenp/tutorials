package com.baeldung.hateoas.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.hateoas.persistence.model.Foo;

public interface IFooDao extends JpaRepository<Foo, Long> {
    
}
