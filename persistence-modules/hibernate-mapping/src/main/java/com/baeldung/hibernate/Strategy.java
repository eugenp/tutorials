package com.baeldung.hibernate;


import java.util.Arrays;
import java.util.List;

public enum Strategy {
    //See that the classes belongs to different packages
    MAP_KEY_COLUMN_BASED(Arrays.asList(com.baeldung.hibernate.mapkeycolumn.Order.class,
            com.baeldung.hibernate.basicannotation.Course.class));


    private List<Class<?>> entityClasses;

    Strategy(List<Class<?>> entityClasses) {
        this.entityClasses = entityClasses;
    }

    public List<Class<?>> getEntityClasses() {
        return entityClasses;
    }
}
