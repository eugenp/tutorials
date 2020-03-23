package com.baeldung.port.outbound.common;

import java.io.Serializable;

public interface IGenericDao<T extends Serializable> extends IOperations<T> {
    //
}
