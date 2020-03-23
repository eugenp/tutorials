package com.baeldung.port.inbound;

import com.baeldung.port.outbound.common.IAuditOperations;
import com.baeldung.port.model.Bar;

public interface IBarAuditableService extends IBarService, IAuditOperations<Bar> {

}
