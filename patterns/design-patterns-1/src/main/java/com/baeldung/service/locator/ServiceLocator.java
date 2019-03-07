package com.baeldung.service.locator;

/**
 * Created by Gebruiker on 4/20/2018.
 */
public class ServiceLocator {

    private static Cache cache;

    static {
        cache = new Cache();
    }

    public static MessagingService getService(String serviceName){

        MessagingService service = cache.getService(serviceName);

        if(service != null){
            return service;
        }

        InitialContext context = new InitialContext();
        MessagingService service1 = (MessagingService)context.lookup(serviceName);
        cache.addService(service1);
        return service1;
    }
}
