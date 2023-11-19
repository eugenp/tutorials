package com.baeldung;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.ReactiveResourceServerApplication.GreetingController;
import com.baeldung.ReactiveResourceServerApplication.MessageService;
import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockAuthentication;
import com.c4_soft.springaddons.security.oauth2.test.annotations.parameterized.AuthenticationSource;
import com.c4_soft.springaddons.security.oauth2.test.annotations.parameterized.ParameterizedAuthentication;

import reactor.core.publisher.Mono;

@WebFluxTest(controllers = GreetingController.class, properties = { "server.ssl.enabled=false" })
class SpringAddonsGreetingControllerUnitTest {

    @MockBean
    MessageService messageService;

    @Autowired
    WebTestClient api;

    /*-----------------------------------------------------------------------------*/
    /* /greet */
    /*
     * This end-point secured with ".anyRequest().authenticated()" in SecurityConf
     */
    /*-----------------------------------------------------------------------------*/

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetGreet_thenUnauthorized() throws Exception {
        api.get().uri("/greet").exchange().expectStatus().isUnauthorized();
    }

    @ParameterizedTest
    @AuthenticationSource({
            @WithMockAuthentication(authorities = { "admin", "ROLE_AUTHORIZED_PERSONNEL" }, name = "ch4mpy"),
            @WithMockAuthentication(authorities = { "uncle", "PIRATE" }, name = "tonton-pirate") })
    void givenUserIsAuthenticated_whenGetGreet_thenOk(@ParameterizedAuthentication Authentication auth) throws Exception {
        final var greeting = "Whatever the service returns";
        when(messageService.greet()).thenReturn(Mono.just(greeting));

        api.get().uri("/greet").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo(greeting);

        verify(messageService, times(1)).greet();
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
    /* /secured-route */
    /*
     * This end-point is secured with
     * ".requestMatchers("/secured-route").hasRole("AUTHORIZED_PERSONNEL")" in
     * SecurityConf
     */
    /*---------------------------------------------------------------------------------------------------------------------*/

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetSecuredRoute_thenUnauthorized() throws Exception {
        api.get().uri("/secured-route").exchange().expectStatus().isUnauthorized();
    }

    @Test
    @WithMockAuthentication("ROLE_AUTHORIZED_PERSONNEL")
    void givenUserIsGrantedWithRoleAuthorizedPersonnel_whenGetSecuredRoute_thenOk() throws Exception {
        final var secret = "Secret!";
        when(messageService.getSecret()).thenReturn(Mono.just(secret));

        api.get().uri("/secured-route").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo(secret);
    }

    @Test
    @WithMockAuthentication("admin")
    void givenUserIsNotGrantedWithRoleAuthorizedPersonnel_whenGetSecuredRoute_thenForbidden() throws Exception {
        api.get().uri("/secured-route").exchange().expectStatus().isForbidden();
    }

    /*---------------------------------------------------------------------------------------------------------*/
    /* /secured-method */
    /*
     * This end-point is secured with
     * "@PreAuthorize("hasRole('AUTHORIZED_PERSONNEL')")" on @Controller method
     */
    /*---------------------------------------------------------------------------------------------------------*/

    @Test
    @WithAnonymousUser
    void givenRequestIsAnonymous_whenGetSecuredMethod_thenUnauthorized() throws Exception {
        api.get().uri("/secured-method").exchange().expectStatus().isUnauthorized();
    }

    @Test
    @WithMockAuthentication("ROLE_AUTHORIZED_PERSONNEL")
    void givenUserIsGrantedWithRoleAuthorizedPersonnel_whenGetSecuredMethod_thenOk() throws Exception {
        final var secret = "Secret!";
        when(messageService.getSecret()).thenReturn(Mono.just(secret));

        api.get().uri("/secured-method").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo(secret);
    }

    @Test
    @WithMockAuthentication("admin")
    void givenUserIsNotGrantedWithRoleAuthorizedPersonnel_whenGetSecuredMethod_thenForbidden() throws Exception {
        api.get().uri("/secured-method").exchange().expectStatus().isForbidden();
    }

}
