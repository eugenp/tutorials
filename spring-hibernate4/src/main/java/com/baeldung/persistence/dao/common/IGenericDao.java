package com.baeldung.persistence.dao.common;

import java.io.Serializable;

interface IGenericDao<T extends Serializable> extends IOperations<T> {
    //
}
