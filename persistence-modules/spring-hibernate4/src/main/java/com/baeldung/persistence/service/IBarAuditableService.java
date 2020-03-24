package com.baeldung.persistence.service;

import com.baeldung.persistence.dao.common.IAuditOperations;
import com.baeldung.persistence.model.Bar;

public interface IBarAuditableService extends IBarService, IAuditOperations<Bar> {

}
