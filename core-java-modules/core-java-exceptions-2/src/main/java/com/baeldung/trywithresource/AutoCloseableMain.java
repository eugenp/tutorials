package com.baeldung.trywithresource;

public class AutoCloseableMain {

    public static void main(String[] args) throws Exception {
        orderOfClosingResources();
    }

    private static void orderOfClosingResources() throws Exception {
        try (AutoCloseableResourcesFirst af = new AutoCloseableResourcesFirst();
             AutoCloseableResourcesSecond as = new AutoCloseableResourcesSecond()) {
            af.doSomething();
            as.doSomething();
        }
    }
}