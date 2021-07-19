package com.baeldung.integrationtesting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.integrationtesting.SecuredService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecuredMethodSpringBootIntegrationTest {

    @Autowired
    private SecuredService service;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void givenUnauthenticated_whenCallService_thenThrowsException() {
        service.sayHelloSecured();
    }

    @WithMockUser(username="spring")
    @Test
    public void givenAuthenticated_whenCallServiceWithSecured_thenOk() {
        assertThat(service.sayHelloSecured()).isNotBlank();
    }   
}
