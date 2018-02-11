package com.baeldung.dsl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

public class ClientErrorLoggingDsl extends AbstractHttpConfigurer<ClientErrorLoggingDsl, HttpSecurity> {

    private List<HttpStatus> errorCodes;

    public ClientErrorLoggingDsl(List<HttpStatus> errorCodes) {
        this.errorCodes = errorCodes;
    }

    public ClientErrorLoggingDsl() {

    }

    @Override
    public void init(HttpSecurity http) throws Exception {
        // initialization code
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(new ClientErrorLoggingFilter(errorCodes), FilterSecurityInterceptor.class);
    }

}
