package com.baeldung.dddhexagonalspringboot.business;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.dddhexagonalspringboot.domain.WorkOrder;
import com.baeldung.dddhexagonalspringboot.port.inbound.ClientPort;
import com.baeldung.dddhexagonalspringboot.port.outbound.WorkOrderPort;

@Component
public class WorkOrderBusinessService implements ClientPort {

    @Autowired
    private WorkOrderPort outboundPort;

    @Override
    public Long createWorkOrder() {
        WorkOrder workOrder = new WorkOrder();
        workOrder.setDate(LocalDateTime.now());
        return outboundPort.create(workOrder)
            .getId();
    }

    @Override
    public WorkOrder get(Long id) {
        return outboundPort.read(id);
    }

}
