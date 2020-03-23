package com.baeldung.port.outbound;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.port.model.Foo;

public interface IFooDao extends JpaRepository<Foo, Long> {
    
}
