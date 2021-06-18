package com.baeldung.config;


import com.baeldung.interfaces.IAccountService;
import com.baeldung.services.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public IAccountService getEmployeeService() {
        return new AccountService();
    }
}
