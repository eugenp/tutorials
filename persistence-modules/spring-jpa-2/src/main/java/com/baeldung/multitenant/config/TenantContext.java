package com.baeldung.multitenant.config;

public class TenantContext {

    private static final ThreadLocal<Object> currentTenant = new ThreadLocal<>();

    public static Object getCurrentTenant() {
        return currentTenant.get();
    }

    public static void setCurrentTenant(Object tenant) {
        currentTenant.set(tenant);
    }
}
