package com.baeldung.dddhexagonalspringboot.port.inbound;

import com.baeldung.dddhexagonalspringboot.domain.WorkOrder;

public interface ClientPort {
    Long createWorkOrder();
    WorkOrder get(Long id);
}
