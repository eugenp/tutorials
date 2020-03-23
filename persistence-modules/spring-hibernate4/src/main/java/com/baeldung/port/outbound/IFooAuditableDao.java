package com.baeldung.port.outbound;

import com.baeldung.port.outbound.common.IAuditOperations;
import com.baeldung.port.model.Foo;

public interface IFooAuditableDao extends IFooDao, IAuditOperations<Foo> {
    //
}