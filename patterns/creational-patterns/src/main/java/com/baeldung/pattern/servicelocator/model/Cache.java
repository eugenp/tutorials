package com.baeldung.pattern.servicelocator.model;

import java.util.ArrayList;
import java.util.List;

public class Cache {
    private List<IService> services;

    public Cache() {
        services = new ArrayList<IService>();
    }

    public IService getService(String serviceName) {

        for (IService service : services) {
            if (service.getName()
                .equalsIgnoreCase(serviceName)) {
                return service;
            }
        }
        return null;
    }

    public void addService(IService newService) {
        boolean exists = false;

        for (IService service : services) {
            if (service.getName()
                .equalsIgnoreCase(newService.getName())) {
                exists = true;
            }
        }
        if (!exists) {
            services.add(newService);
        }
    }
}