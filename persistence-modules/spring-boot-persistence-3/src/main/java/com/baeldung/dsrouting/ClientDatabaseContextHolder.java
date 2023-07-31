package com.baeldung.dsrouting;

import org.springframework.util.Assert;

/**
 * Thread shared context to point to the datasource which should be used. This
 * enables context switches between different clients.
 */
public class ClientDatabaseContextHolder {

    private static final ThreadLocal<ClientDatabase> CONTEXT = new ThreadLocal<>();

    public static void set(ClientDatabase clientDatabase) {
        Assert.notNull(clientDatabase, "clientDatabase cannot be null");
        CONTEXT.set(clientDatabase);
    }

    public static ClientDatabase getClientDatabase() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }

}
