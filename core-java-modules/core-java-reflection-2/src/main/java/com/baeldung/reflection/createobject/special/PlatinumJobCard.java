package com.baeldung.reflection.createobject.special;

import java.lang.reflect.InvocationTargetException;

public class PlatinumJobCard<T extends Job> {
    private T jobType;

    public void setJobType(Class<T> jobTypeClass) throws
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.jobType = jobTypeClass.getDeclaredConstructor().newInstance();
    }

    public String startJob() {
        return "Start Platinum " + this.jobType.getJobType();
    }
}
