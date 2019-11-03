package com.baeldung.persistence.dao;

import com.baeldung.persistence.dao.common.IAuditOperations;
import com.baeldung.persistence.model.Foo;

public interface IFooAuditableDao extends IFooDao, IAuditOperations<Foo> {
    //
}