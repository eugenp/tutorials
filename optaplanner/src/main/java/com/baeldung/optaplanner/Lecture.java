package com.baeldung.optaplanner;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Lecture {

    @PlanningId
    private Long id;
    private Integer roomNumber;
    private Integer period;

    public Lecture(Long i) {
        this.id = i;
    }

    public Lecture() {
    }

    @PlanningVariable(valueRangeProviderRefs = {"availablePeriods"})
    public Integer getPeriod() {
        return period;
    }

    @PlanningVariable(valueRangeProviderRefs = {"availableRooms"})
    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

}
