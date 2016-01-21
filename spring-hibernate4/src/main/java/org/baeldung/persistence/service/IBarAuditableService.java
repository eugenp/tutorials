package org.baeldung.persistence.service;

import org.baeldung.persistence.dao.common.IAuditOperations;
import org.baeldung.persistence.model.Bar;

public interface IBarAuditableService extends IBarService, IAuditOperations<Bar> {

}
