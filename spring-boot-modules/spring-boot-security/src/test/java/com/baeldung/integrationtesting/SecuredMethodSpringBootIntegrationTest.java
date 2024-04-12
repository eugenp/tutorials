package com.baeldung.integrationtesting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
class SecuredMethodSpringBootIntegrationTest {

    @Autowired
    private SecuredService service;

    @Test
    void givenUnauthenticated_whenCallService_thenThrowsException() {
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> service.sayHelloSecured());
        assertThat(illegalArgumentException).hasMessageContaining("authenticated");
    }

    @WithMockUser(username = "spring")
    @Test
    void givenAuthenticated_whenCallServiceWithSecured_thenOk() {
        assertThat(service.sayHelloSecured()).isNotBlank();
    }
}
