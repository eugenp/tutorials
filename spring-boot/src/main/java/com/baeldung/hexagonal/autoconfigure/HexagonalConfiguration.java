package com.baeldung.hexagonal.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.hexagonal.adapters.*;
import com.baeldung.hexagonal.ports.*;

@Configuration
public class HexagonalConfiguration {

    @Bean
    public ContactRepository contactRepository() {
        return new InMemoryContactRepository();
    }

    @Bean
    public ContactService contactService(ContactRepository contactRepository, EmailValidator emailValidator, PhoneValidator phoneValidator) {
        return new ContactServiceImpl(contactRepository, emailValidator, phoneValidator);
    }

    @Bean
    public EmailValidator emailValidator() {
        return new RegexEmailValidator();
    }

    @Bean
    public PhoneValidator phoneValidator() {
        return new RegexPhoneValidator();
    }

}
