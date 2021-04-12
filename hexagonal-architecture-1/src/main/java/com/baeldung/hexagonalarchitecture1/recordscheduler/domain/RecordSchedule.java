package com.baeldung.hexagonalarchitecture1.recordscheduler.domain;

import java.util.Date;

public class RecordSchedule {

    private String userId;
    private String programId;
    private Date startTime;
    private Date endTime;

    public RecordSchedule(String userId, String programId, Date startTime, Date endTime) {
        this.userId = userId;
        this.programId = programId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
