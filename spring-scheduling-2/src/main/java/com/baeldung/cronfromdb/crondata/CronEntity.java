package com.baeldung.cronfromdb.crondata;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cron_config")
public class CronEntity {

    @Id
    private Long id;

    private String cronExpression;

    public CronEntity() {
    }

    public CronEntity(Long id, String cronExpression) {
        this.id = id;
        this.cronExpression = cronExpression;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}