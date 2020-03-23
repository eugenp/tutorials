package com.baeldung.port.inbound;

import com.baeldung.port.model.Foo;
import com.baeldung.port.outbound.common.IOperations;

public interface IFooService extends IOperations<Foo> {
    //
}
