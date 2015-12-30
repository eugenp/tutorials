package org.baeldung.persistence.service;

import org.baeldung.persistence.dao.common.IAuditOperations;
import org.baeldung.persistence.model.Foo;

public interface IFooAuditableService extends IFooService, IAuditOperations<Foo> {
    //
}
