package com.baeldung.persistence.service;

import com.baeldung.persistence.dao.common.IAuditOperations;
import com.baeldung.persistence.model.Foo;

public interface IFooAuditableService extends IFooService, IAuditOperations<Foo> {
    //
}
