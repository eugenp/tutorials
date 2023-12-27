package com.baeldung.timefoldsolver;

import java.time.LocalDateTime;

import ai.timefold.solver.core.api.domain.entity.PlanningEntity;
import ai.timefold.solver.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Shift {

    private LocalDateTime start;
    private LocalDateTime end;
    private String requiredSkill;

    @PlanningVariable
    private Employee employee;

    // A no-arg constructor is required for @PlanningEntity annotated classes
    public Shift() {
    }

    public Shift(LocalDateTime start, LocalDateTime end, String requiredSkill) {
        this(start, end, requiredSkill, null);
    }

    public Shift(LocalDateTime start, LocalDateTime end, String requiredSkill, Employee employee) {
        this.start = start;
        this.end = end;
        this.requiredSkill = requiredSkill;
        this.employee = employee;
    }

    @Override
    public String toString() {
        return start + " - " + end;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public String getRequiredSkill() {
        return requiredSkill;
    }

    public Employee getEmployee() {
        return employee;
    }

}
