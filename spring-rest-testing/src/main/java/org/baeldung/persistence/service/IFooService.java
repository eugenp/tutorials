package org.baeldung.persistence.service;

import org.baeldung.persistence.IOperations;
import org.baeldung.persistence.model.Foo;

public interface IFooService extends IOperations<Foo> {

    Foo retrieveByName(String name);

}
