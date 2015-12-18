package org.baeldung.persistence.dao;

import org.baeldung.persistence.dao.common.IAuditOperations;
import org.baeldung.persistence.model.Foo;

public interface IFooAuditableDao extends IFooDao, IAuditOperations<Foo> {
    //
}