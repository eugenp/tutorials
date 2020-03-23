package com.baeldung.port.inbound;

import com.baeldung.port.outbound.common.IAuditOperations;
import com.baeldung.port.model.Foo;

public interface IFooAuditableService extends IFooService, IAuditOperations<Foo> {
    //
}
