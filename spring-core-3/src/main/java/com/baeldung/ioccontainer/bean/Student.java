package com.baeldung.ioccontainer.bean;

import org.springframework.context.annotation.Lazy;

@Lazy
public class Student {
    private static boolean isBeanInstantiated = false;

    public void postConstruct() {
        setBeanInstantiated(true);
    }

    public static boolean isBeanInstantiated() {
        return isBeanInstantiated;
    }

    public static void setBeanInstantiated(boolean isBeanInstantiated) {
        Student.isBeanInstantiated = isBeanInstantiated;
    }
}
