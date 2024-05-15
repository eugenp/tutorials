package com.baeldung.reflection.createobject.basic;

import java.lang.reflect.InvocationTargetException;

public class BronzeJobCard {
    private Object jobType;
    public void setJobType(String jobType) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class jobTypeClass = Class.forName(jobType);
        this.jobType = jobTypeClass.getDeclaredConstructor().newInstance();
    }
    public String startJob() {
        if(this.jobType instanceof RepairJob) {
            return "Start Bronze " + ((RepairJob) this.jobType).getJobType();
        }
        if(this.jobType instanceof MaintenanceJob) {
            return "Start Bronze " + ((MaintenanceJob) this.jobType).getJobType();
        }
        return "Bronze Job Failed";
    }
}
