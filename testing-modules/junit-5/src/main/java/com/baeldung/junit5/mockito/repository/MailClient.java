package com.baeldung.junit5.mockito.repository;

import com.baeldung.junit5.mockito.User;

public interface MailClient {

    void sendUserRegistrationMail(User user);
    
}
