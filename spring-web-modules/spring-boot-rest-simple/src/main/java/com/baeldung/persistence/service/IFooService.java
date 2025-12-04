package com.baeldung.persistence.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.baeldung.persistence.IOperations;
import com.baeldung.persistence.model.Foo;

public interface IFooService extends IOperations<Foo> {
    
    Page<Foo> findPaginated(Pageable pageable);

}
