package com.baeldung.tutorial.hexagonal.adapter;

import com.baeldung.tutorial.hexagonal.port.in.UserRegistration;

/**
 * User Registration data is populated in this class from REST API
 * Example, No Implementation
 */
public class RestUserRegistrationImpl implements UserRegistration {

    public boolean subscriptionPaid() {
        return false;
    }


    public String getUserName() {
        return null;
    }


    public String getUserEmail() {
        return null;
    }

    public String getUserAddress() {
        return null;
    }
}
