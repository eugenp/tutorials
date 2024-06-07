package com.baeldung.bff;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;

import com.c4_soft.springaddons.security.oauth2.test.webflux.AddonsWebfluxTestConf;
import com.c4_soft.springaddons.security.oauth2.test.webflux.WebTestClientSupport;

@SpringBootTest
@AutoConfigureWebTestClient
@ImportAutoConfiguration(AddonsWebfluxTestConf.class)
class BffApplicationIntegrationTest {
    @Autowired
    WebTestClientSupport api;

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetLoginOptions_thenOk() throws Exception {
        api.get("/login-options")
            .expectStatus()
            .isOk()
            .expectBody()
            .jsonPath("$")
            .isArray()
            .jsonPath("$[0].label")
            .isEqualTo("baeldung")
            .jsonPath("$[0].loginUri")
            .isEqualTo("http://localhost:7080/bff/oauth2/authorization/baeldung")
            .jsonPath("$[0].isSameAuthority")
            .isEqualTo(true);
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetLiveness_thenOk() throws Exception {
        api.get("/actuator/health/liveness")
            .expectStatus()
            .isOk();
    }

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetReadiness_thenOk() throws Exception {
        api.get("/actuator/health/readiness")
            .expectStatus()
            .isOk();
    }

}
