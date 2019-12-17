package com.baeldung.dddhexagonalspringboot.adapters.secondary.workorder.rabbitmq;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.baeldung.dddhexagonalspringboot.domain.WorkOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkOrderJSON {

    private Long id;
    private String date;

    public static WorkOrderJSON fromDomain(WorkOrder workOrder) {
        WorkOrderJSON woJSON = new WorkOrderJSON();
        woJSON.setId(workOrder.getId());
        woJSON.setDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(workOrder.getDate()));
        return woJSON;
    }

    public static WorkOrder toDomain(WorkOrderJSON woJSON) {
        WorkOrder workOrder = new WorkOrder();
        workOrder.setId(woJSON.getId());
        workOrder.setDate(LocalDateTime.parse(woJSON.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        return workOrder;
    }
}
