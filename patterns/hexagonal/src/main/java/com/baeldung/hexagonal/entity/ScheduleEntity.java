package com.baeldung.hexagonal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@Entity
public class ScheduleEntity {

    @Id
    private Long id;

    @Column
    private Instant arrivalTime;

    public ScheduleEntity(Long id, Instant arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    public ScheduleEntity() {
    }

    public Long getId() {
        return id;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }
}
