package com.baeldung.dddhexagonalspringboot.adapters.primary.client;

import java.time.format.DateTimeFormatter;

import com.baeldung.dddhexagonalspringboot.domain.WorkOrder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkOrderClientDTO {

    private Long id;
    private String date;

    public static WorkOrderClientDTO fromDomain(WorkOrder workOrder) {
        WorkOrderClientDTO dto = new WorkOrderClientDTO();
        if (null != workOrder.getId()) {
            dto.setId(workOrder.getId());
            dto.setDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(workOrder.getDate()));
        }
        return dto;
    }
}
