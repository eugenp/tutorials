package com.baeldung.designpatterns.service.locator;

/**
 * Created by Gebruiker on 4/20/2018.
 */
public class ServiceLocator {
    private static Cache cache;

    static {
        cache = new Cache();
    }

    public static MessagingService getService(String jndiName){

        MessagingService service = cache.getService(jndiName);

        if(service != null){
            return service;
        }

        InitialContext context = new InitialContext();
        MessagingService service1 = (MessagingService)context.lookup(jndiName);
        cache.addService(service1);
        return service1;
    }
}
