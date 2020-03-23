package com.baeldung.port.outbound;

import com.baeldung.port.model.Foo;
import com.baeldung.port.outbound.common.IOperations;

public interface IFooDao extends IOperations<Foo> {
    //
}
