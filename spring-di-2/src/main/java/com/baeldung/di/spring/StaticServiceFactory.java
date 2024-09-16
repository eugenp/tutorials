package com.baeldung.di.spring;

public class StaticServiceFactory {
    public static IService getService(int number) {
        switch (number) {
        case 1:
            return new MessageService("Foo");
        case 0:
            return new IndexService();
        default:
            throw new IllegalArgumentException("Unknown parameter " + number);
        }
    }
}
