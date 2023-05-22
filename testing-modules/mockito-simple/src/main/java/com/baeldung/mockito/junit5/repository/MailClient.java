package com.baeldung.mockito.junit5.repository;

import com.baeldung.mockito.junit5.User;

public interface MailClient {

    void sendUserRegistrationMail(User user);

}
