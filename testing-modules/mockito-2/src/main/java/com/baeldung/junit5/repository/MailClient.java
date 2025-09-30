package com.baeldung.junit5.repository;

import com.baeldung.junit5.User;

public interface MailClient {

    void sendUserRegistrationMail(User user);

}
