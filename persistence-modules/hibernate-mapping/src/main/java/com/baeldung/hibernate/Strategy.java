package com.baeldung.hibernate;


import java.util.Arrays;
import java.util.List;

public enum Strategy {
    //See that the classes belongs to different packages
    MAP_KEY_COLUMN_BASED(Arrays.asList(com.baeldung.hibernate.persistmaps.mapkeycolumn.Order.class, 
            com.baeldung.hibernate.basicannotation.Course.class)),
    MAP_KEY_BASED(Arrays.asList(com.baeldung.hibernate.persistmaps.mapkey.Item.class,
            com.baeldung.hibernate.persistmaps.mapkey.Order.class,com.baeldung.hibernate.persistmaps.mapkey.User.class)),
    MAP_KEY_JOIN_COLUMN_BASED(Arrays.asList(com.baeldung.hibernate.persistmaps.mapkeyjoincolumn.Seller.class,
            com.baeldung.hibernate.persistmaps.mapkeyjoincolumn.Item.class,
            com.baeldung.hibernate.persistmaps.mapkeyjoincolumn.Order.class)),
    MAP_KEY_ENUMERATED_BASED(Arrays.asList(com.baeldung.hibernate.persistmaps.mapkeyenumerated.Order.class,
            com.baeldung.hibernate.persistmaps.mapkey.Item.class)),
    MAP_KEY_TEMPORAL_BASED(Arrays.asList(com.baeldung.hibernate.persistmaps.mapkeytemporal.Order.class,
            com.baeldung.hibernate.persistmaps.mapkey.Item.class));


    private List<Class<?>> entityClasses;

    Strategy(List<Class<?>> entityClasses) {
        this.entityClasses = entityClasses;
    }

    public List<Class<?>> getEntityClasses() {
        return entityClasses;
    }
}
