package com.baeldung.pattern.servicelocator.application;

import com.baeldung.pattern.servicelocator.model.IService;
import com.baeldung.pattern.servicelocator.model.ServiceLocator;

public class Application {

    public static void main(String[] args) {
        IService service = ServiceLocator.getService("Service1");
        service.execute();
        service = ServiceLocator.getService("Service2");
        service.execute();
        service = ServiceLocator.getService("Service1");
        service.execute();
        service = ServiceLocator.getService("Service2");
        service.execute();
    }
}