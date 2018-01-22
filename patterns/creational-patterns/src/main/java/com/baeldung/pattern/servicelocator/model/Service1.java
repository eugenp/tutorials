package com.baeldung.pattern.servicelocator.model;

public class Service1 implements IService {
    public void execute() {
        System.out.println("Executing Service1");
    }

    @Override
    public String getName() {
        return "Service1";
    }
}