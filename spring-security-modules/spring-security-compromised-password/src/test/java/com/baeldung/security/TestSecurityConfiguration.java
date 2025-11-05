package com.baeldung.security;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;

@TestConfiguration
public class TestSecurityConfiguration {

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new PasswordCheckerSimulator();
    }

}