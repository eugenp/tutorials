package com.baeldung.persistence.dao.common;

import java.io.Serializable;
import java.util.List;

public interface IAuditOperations<T extends Serializable> {

    List<T> getEntitiesAtRevision(Number revision);

    List<T> getEntitiesModifiedAtRevision(Number revision);

    List<T> getRevisions();

}
