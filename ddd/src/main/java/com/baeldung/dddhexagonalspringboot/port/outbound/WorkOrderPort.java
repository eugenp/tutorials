package com.baeldung.dddhexagonalspringboot.port.outbound;

import com.baeldung.dddhexagonalspringboot.domain.WorkOrder;

public interface WorkOrderPort {
    WorkOrder create(WorkOrder workOrder);
    WorkOrder read(Long id);
}
