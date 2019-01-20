package com.baeldung.tutorial.hexagonal.port.in;

/**
 * This is an in port to the core. This interface feeds the data to the core of the application.
 * This interface can be used either by UI or REST API or any other framework to populate the user data.
 * If we observe, the user registration password is not maintained in core. This is upto the application on how to handle it
 * It can be maintained either by using Single Singon, LDAP, Database, OAuth2 etc.
 */
public interface UserRegistration {
    String getUserName();
    String getUserEmail();
    String getUserAddress();
    boolean subscriptionPaid();
}
