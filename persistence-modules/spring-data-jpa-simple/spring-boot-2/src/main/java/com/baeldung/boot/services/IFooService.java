package com.baeldung.boot.services;

import com.baeldung.boot.domain.Foo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IFooService extends IOperations<Foo> {

    Foo retrieveByName(String name);
    
    Page<Foo> findPaginated(Pageable pageable);

}
