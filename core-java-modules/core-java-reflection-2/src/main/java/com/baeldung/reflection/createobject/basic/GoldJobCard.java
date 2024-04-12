package com.baeldung.reflection.createobject.basic;

import java.lang.reflect.InvocationTargetException;

public class GoldJobCard<T> {
    private T jobType;

    public void setJobType(Class<T> jobTypeClass) throws
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.jobType = jobTypeClass.getDeclaredConstructor().newInstance();
    }

    public String startJob() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return "Start Gold " + this.jobType.getClass().getMethod("getJobType")
                .invoke(this.jobType).toString();
    }
}
