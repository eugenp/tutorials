package com.baeldung;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.c4_soft.springaddons.security.oauth2.test.annotations.OpenIdClaims;
import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockJwtAuth;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureWebTestClient
class ReactiveResourceServerApplicationIntegrationTest {
    @Autowired
    WebTestClient api;

    /*-----------------------------------------------------------------------------*/
    /* /greet                                                                      */
    /* This end-point secured with ".anyRequest().authenticated()" in SecurityConf */
    /*-----------------------------------------------------------------------------*/

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetGreet_thenUnauthorized() throws Exception {
        api.get()
            .uri("/greet")
            .exchange()
            .expectStatus()
            .isUnauthorized();
    }

    @Test
    @WithMockJwtAuth(authorities = { "admin", "ROLE_AUTHORIZED_PERSONNEL" }, claims = @OpenIdClaims(preferredUsername = "ch4mpy"))
    void givenUserIsAuthenticated_whenGetGreet_thenOk() throws Exception {
        api.get()
            .uri("/greet")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .isEqualTo("Hello ch4mpy! You are granted with [admin, ROLE_AUTHORIZED_PERSONNEL].");
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
    /* /secured-route                                                                                                      */
    /* This end-point is secured with ".requestMatchers("/secured-route").hasRole("AUTHORIZED_PERSONNEL")" in SecurityConf */
    /*---------------------------------------------------------------------------------------------------------------------*/

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetSecuredRoute_thenUnauthorized() throws Exception {
        api.get()
            .uri("/secured-route")
            .exchange()
            .expectStatus()
            .isUnauthorized();
    }

    @Test
    @WithMockJwtAuth("ROLE_AUTHORIZED_PERSONNEL")
    void givenUserIsGrantedWithRoleAuthorizedPersonnel_whenGetSecuredRoute_thenOk() throws Exception {
        api.get()
            .uri("/secured-route")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .isEqualTo("Only authorized personnel can read that");
    }

    @Test
    @WithMockJwtAuth("admin")
    void givenUserIsNotGrantedWithRoleAuthorizedPersonnel_whenGetSecuredRoute_thenForbidden() throws Exception {
        api.get()
            .uri("/secured-route")
            .exchange()
            .expectStatus()
            .isForbidden();
    }

    /*---------------------------------------------------------------------------------------------------------*/
    /* /secured-method                                                                                         */
    /* This end-point is secured with "@PreAuthorize("hasRole('AUTHORIZED_PERSONNEL')")" on @Controller method */
    /*---------------------------------------------------------------------------------------------------------*/

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetSecuredMethod_thenUnauthorized() throws Exception {
        api.get()
            .uri("/secured-method")
            .exchange()
            .expectStatus()
            .isUnauthorized();
    }

    @Test
    @WithMockJwtAuth("ROLE_AUTHORIZED_PERSONNEL")
    void givenUserIsGrantedWithRoleAuthorizedPersonnel_whenGetSecuredMethod_thenOk() throws Exception {
        api.get()
            .uri("/secured-method")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .isEqualTo("Only authorized personnel can read that");
    }

    @Test
    @WithMockJwtAuth("admin")
    void givenUserIsNotGrantedWithRoleAuthorizedPersonnel_whenGetSecuredMethod_thenForbidden() throws Exception {
        api.get()
            .uri("/secured-method")
            .exchange()
            .expectStatus()
            .isForbidden();
    }
}
