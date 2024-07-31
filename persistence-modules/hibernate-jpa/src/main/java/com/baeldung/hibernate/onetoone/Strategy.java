package com.baeldung.hibernate.onetoone;


import java.util.Arrays;
import java.util.List;

public enum Strategy {
    //See that the classes belongs to different packages
    FOREIGN_KEY(Arrays.asList(com.baeldung.hibernate.onetoone.foreignkeybased.User.class,
            com.baeldung.hibernate.onetoone.foreignkeybased.Address.class)),
    SHARED_PRIMARY_KEY(Arrays.asList(com.baeldung.hibernate.onetoone.sharedkeybased.User.class,
            com.baeldung.hibernate.onetoone.sharedkeybased.Address.class)),
    JOIN_TABLE_BASED(Arrays.asList(com.baeldung.hibernate.onetoone.jointablebased.Employee.class,
            com.baeldung.hibernate.onetoone.jointablebased.WorkStation.class));

    private List<Class<?>> entityClasses;

    Strategy(List<Class<?>> entityClasses) {
        this.entityClasses = entityClasses;
    }

    public List<Class<?>> getEntityClasses() {
        return entityClasses;
    }
}
