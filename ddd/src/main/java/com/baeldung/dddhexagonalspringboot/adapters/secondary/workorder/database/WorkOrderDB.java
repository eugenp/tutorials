package com.baeldung.dddhexagonalspringboot.adapters.secondary.workorder.database;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.baeldung.dddhexagonalspringboot.domain.WorkOrder;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WorkOrderDB {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime date;

    public static WorkOrderDB fromDomain(WorkOrder workOrder) {
        WorkOrderDB woDB = new WorkOrderDB();
        woDB.setId(workOrder.getId());
        woDB.setDate(workOrder.getDate());
        return woDB;
    }

    public static WorkOrder toDomain(WorkOrderDB woDB) {
        WorkOrder workOrder = new WorkOrder();
        workOrder.setId(woDB.getId());
        workOrder.setDate(woDB.getDate());
        return workOrder;
    }

}
