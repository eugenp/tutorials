package com.baeldung.ioccontainer.bean;

public class Student {
    public static boolean isBeanInstantiated = false;

    public void postConstruct() {
        isBeanInstantiated = true;
    }
}
