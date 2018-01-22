package com.baeldung.pattern.servicelocator.model;

public class ServiceLocator {
    private static Cache cache;

    static {
        cache = new Cache();
    }

    public static IService getService(String jndiName) {

        IService service = cache.getService(jndiName);

        if (service != null) {
            return service;
        }

        InitialContext context = new InitialContext();
        IService service1 = (IService) context.lookup(jndiName);
        cache.addService(service1);
        return service1;
    }
}
