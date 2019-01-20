package com.baeldung.tutorial.hexagonal.port.out;

import com.baeldung.tutorial.hexagonal.core.User;
import com.baeldung.tutorial.hexagonal.core.UserType;

/**
 * Fetch the User Registered Details from the core
 * This can be used by any UI or any other REST API to get the registered User Details.
 */
public interface RegisteredUserDetails {
    User getUser(String emailId);
    Long getUserUnqiueId(String emailId);
    UserType getUserType(String emailId);
}