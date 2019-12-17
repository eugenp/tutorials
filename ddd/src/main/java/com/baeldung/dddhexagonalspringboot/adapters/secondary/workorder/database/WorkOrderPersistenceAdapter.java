package com.baeldung.dddhexagonalspringboot.adapters.secondary.workorder.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.baeldung.dddhexagonalspringboot.domain.WorkOrder;
import com.baeldung.dddhexagonalspringboot.port.outbound.WorkOrderPort;

@Component
@Profile("database")
public class WorkOrderPersistenceAdapter implements WorkOrderPort {

    @Autowired
    private WorkOrderRepository repository;

    @Override
    public WorkOrder create(WorkOrder workOrder) {
        return WorkOrderDB.toDomain(repository.save(WorkOrderDB.fromDomain(workOrder)));
    }

    @Override
    public WorkOrder read(Long id) {
        return WorkOrderDB.toDomain(repository.findById(id)
            .orElse(new WorkOrderDB()));
    }

}
