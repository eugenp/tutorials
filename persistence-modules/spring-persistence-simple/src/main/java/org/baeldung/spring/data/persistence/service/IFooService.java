package org.baeldung.spring.data.persistence.service;

import org.baeldung.spring.data.persistence.model.Foo;

import com.baeldung.persistence.dao.common.IOperations;

public interface IFooService extends IOperations<Foo> {

    Foo retrieveByName(String name);

}
