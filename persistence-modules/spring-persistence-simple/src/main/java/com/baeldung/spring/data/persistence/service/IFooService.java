package com.baeldung.spring.data.persistence.service;

import com.baeldung.spring.data.persistence.model.Foo;

import com.baeldung.persistence.dao.common.IOperations;

public interface IFooService extends IOperations<Foo> {

    Foo retrieveByName(String name);

}
