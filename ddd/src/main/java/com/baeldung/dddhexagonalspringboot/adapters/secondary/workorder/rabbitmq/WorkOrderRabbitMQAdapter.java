package com.baeldung.dddhexagonalspringboot.adapters.secondary.workorder.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.baeldung.dddhexagonalspringboot.domain.WorkOrder;
import com.baeldung.dddhexagonalspringboot.port.outbound.WorkOrderPort;

@Component
@Profile("rabbitmq")
public class WorkOrderRabbitMQAdapter implements WorkOrderPort {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private Queue queue;

    @Override
    public WorkOrder create(WorkOrder workOrder) {
        WorkOrderJSON woJSON = WorkOrderJSON.fromDomain(workOrder);
        woJSON.setId(System.nanoTime());
        amqpTemplate.convertAndSend(queue.getName(), woJSON);
        return WorkOrderJSON.toDomain(woJSON);
    }

    @Override
    public WorkOrder read(Long id) {
        // Not implemented
        throw new UnsupportedOperationException();
    }

}
