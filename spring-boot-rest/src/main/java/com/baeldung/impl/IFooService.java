package com.baeldung.port.inbound;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.baeldung.port.IOperations;
import com.baeldung.port.model.Foo;

public interface IFooService extends IOperations<Foo> {
    
    Page<Foo> findPaginated(Pageable pageable);

}
