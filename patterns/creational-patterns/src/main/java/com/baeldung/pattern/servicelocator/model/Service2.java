package com.baeldung.pattern.servicelocator.model;

public class Service2 implements IService {
    public void execute() {
        System.out.println("Executing Service2");
    }

    @Override
    public String getName() {
        return "Service2";
    }
}
