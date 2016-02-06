package com.baeldung.persistence.dao;

import com.baeldung.persistence.dao.common.IAuditOperations;
import com.baeldung.persistence.model.Bar;

public interface IBarAuditableDao extends IBarDao, IAuditOperations<Bar> {
    //
}
