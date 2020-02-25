package com.baeldung.hexagonalarchitecture.useraccount;

/**
 * @author Fact S Musingarimi
 * 23/2/2020
 * 13:18
 */
public interface UserRegistrationService {
    UserRegistrationResponse register(UserRegistrationRequest userRegistrationRequest);

    EmailConfirmationResponse confirmEmail(EmailConfirmationRequest emailConfirmationRequest);

}
