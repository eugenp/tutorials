package com.baeldung.hateoas.persistence.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.baeldung.hateoas.persistence.IOperations;
import com.baeldung.hateoas.persistence.model.Foo;

public interface IFooService extends IOperations<Foo> {
    
    Page<Foo> findPaginated(Pageable pageable);

}
