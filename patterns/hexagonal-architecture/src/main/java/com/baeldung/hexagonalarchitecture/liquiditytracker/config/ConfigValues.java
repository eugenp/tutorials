package com.baeldung.hexagonalarchitecture.liquiditytracker.config;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class ConfigValues {

    private MessagingConfigValues messaging;
    private DatabaseConfigValues database;

    @Override
    public String toString() {
        return "ConfigValues [messaging=" + messaging + ", database=" + database + "]";
    }

    public MessagingConfigValues getMessaging() {
        return messaging;
    }

    public void setMessaging(MessagingConfigValues messaging) {
        this.messaging = messaging;
    }

    public DatabaseConfigValues getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseConfigValues database) {
        this.database = database;
    }
}
