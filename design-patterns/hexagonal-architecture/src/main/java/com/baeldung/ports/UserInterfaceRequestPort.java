package com.baeldung.ports;

@FunctionalInterface
public interface UserInterfaceRequestPort {
    void buy(String manufacturer, String model);
}
