package com.baeldung.reflection.createobject.basic;

import java.lang.reflect.InvocationTargetException;

public class SilverJobCard {
    private Object jobType;

    public void setJobType(Class jobTypeClass) throws
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.jobType = jobTypeClass.getDeclaredConstructor().newInstance();
    }

    public String startJob() {
        if (this.jobType instanceof RepairJob) {
            return "Start Silver " + ((RepairJob) this.jobType).getJobType();
        }
        if (this.jobType instanceof MaintenanceJob) {
            return "Start Silver " + ((MaintenanceJob) this.jobType).getJobType();
        }
        return "Silver Job Failed";
    }
}
