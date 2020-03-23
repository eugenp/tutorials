package com.baeldung.port.outbound;

import com.baeldung.port.outbound.common.IAuditOperations;
import com.baeldung.port.model.Bar;

public interface IBarAuditableDao extends IBarDao, IAuditOperations<Bar> {
    //
}
