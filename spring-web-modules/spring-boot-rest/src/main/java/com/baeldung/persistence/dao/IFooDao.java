package com.baeldung.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.persistence.model.Foo;

public interface IFooDao extends JpaRepository<Foo, Long> {
    
}
