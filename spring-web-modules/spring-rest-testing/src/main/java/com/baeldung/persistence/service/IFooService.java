package com.baeldung.persistence.service;

import com.baeldung.persistence.model.Foo;
import com.baeldung.persistence.IOperations;

public interface IFooService extends IOperations<Foo> {

    Foo retrieveByName(String name);

}
