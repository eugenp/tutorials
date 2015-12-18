package org.baeldung.persistence.dao;

import org.baeldung.persistence.dao.common.IAuditOperations;
import org.baeldung.persistence.model.Bar;

public interface IBarAuditableDao extends IBarDao, IAuditOperations<Bar> {
    //
}
