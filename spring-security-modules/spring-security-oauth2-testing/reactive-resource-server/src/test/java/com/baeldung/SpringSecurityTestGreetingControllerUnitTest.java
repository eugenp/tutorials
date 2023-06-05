package com.baeldung;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockAuthentication;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockJwt;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.baeldung.ReactiveResourceServerApplication.GreetingController;
import com.baeldung.ReactiveResourceServerApplication.MessageService;

import reactor.core.publisher.Mono;

@WebFluxTest(controllers = GreetingController.class, properties = { "server.ssl.enabled=false" })
class SpringSecurityTestGreetingControllerUnitTest {
    private static final AnonymousAuthenticationToken ANONYMOUS_AUTHENTICATION = new AnonymousAuthenticationToken("anonymous", "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"));

    @MockBean
    MessageService messageService;

    @Autowired
    WebTestClient api;

    /*-----------------------------------------------------------------------------*/
    /* /greet                                                                      */
    /* This end-point secured with ".anyRequest().authenticated()" in SecurityConf */
    /*-----------------------------------------------------------------------------*/

    @Test
    void givenRequestIsAnonymous_whenGetGreet_thenUnauthorized() throws Exception {
        api.mutateWith(mockAuthentication(ANONYMOUS_AUTHENTICATION))
            .get()
            .uri("/greet")
            .exchange()
            .expectStatus()
            .isUnauthorized();
    }

    @Test
    void givenUserIsAuthenticated_whenGetGreet_thenOk() throws Exception {
        final var greeting = "Whatever the service returns";
        when(messageService.greet()).thenReturn(Mono.just(greeting));

        api.mutateWith(mockJwt().authorities(List.of(new SimpleGrantedAuthority("admin"), new SimpleGrantedAuthority("ROLE_AUTHORIZED_PERSONNEL")))
            .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "ch4mpy")))
            .get()
            .uri("/greet")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .isEqualTo(greeting);

        verify(messageService, times(1)).greet();
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
    /* /secured-route                                                                                                      */
    /* This end-point is secured with ".requestMatchers("/secured-route").hasRole("AUTHORIZED_PERSONNEL")" in SecurityConf */
    /*---------------------------------------------------------------------------------------------------------------------*/

    @Test
    void givenRequestIsAnonymous_whenGetSecuredRoute_thenUnauthorized() throws Exception {
        api.mutateWith(mockAuthentication(ANONYMOUS_AUTHENTICATION))
            .get()
            .uri("/secured-route")
            .exchange()
            .expectStatus()
            .isUnauthorized();
    }

    @Test
    void givenUserIsGrantedWithRoleAuthorizedPersonnel_whenGetSecuredRoute_thenOk() throws Exception {
        final var secret = "Secret!";
        when(messageService.getSecret()).thenReturn(Mono.just(secret));

        api.mutateWith(mockJwt().authorities(new SimpleGrantedAuthority("ROLE_AUTHORIZED_PERSONNEL")))
            .get()
            .uri("/secured-route")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .isEqualTo(secret);
    }

    @Test
    void givenUserIsNotGrantedWithRoleAuthorizedPersonnel_whenGetSecuredRoute_thenForbidden() throws Exception {
        api.mutateWith(mockJwt().authorities(new SimpleGrantedAuthority("admin")))
            .get()
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
    void givenRequestIsAnonymous_whenGetSecuredMethod_thenUnauthorized() throws Exception {
        api.mutateWith(mockAuthentication(ANONYMOUS_AUTHENTICATION))
            .get()
            .uri("/secured-method")
            .exchange()
            .expectStatus()
            .isUnauthorized();
    }

    @Test
    void givenUserIsGrantedWithRoleAuthorizedPersonnel_whenGetSecuredMethod_thenOk() throws Exception {
        final var secret = "Secret!";
        when(messageService.getSecret()).thenReturn(Mono.just(secret));

        api.mutateWith(mockJwt().authorities(new SimpleGrantedAuthority("ROLE_AUTHORIZED_PERSONNEL")))
            .get()
            .uri("/secured-method")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(String.class)
            .isEqualTo(secret);
    }

    @Test
    void givenUserIsNotGrantedWithRoleAuthorizedPersonnel_whenGetSecuredMethod_thenForbidden() throws Exception {
        api.mutateWith(mockJwt().authorities(new SimpleGrantedAuthority("admin")))
            .get()
            .uri("/secured-method")
            .exchange()
            .expectStatus()
            .isForbidden();
    }

}
