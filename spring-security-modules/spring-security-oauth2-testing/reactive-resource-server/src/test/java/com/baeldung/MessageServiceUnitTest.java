package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.ReactiveResourceServerApplication.MessageService;
import com.baeldung.ReactiveResourceServerApplication.SecurityConfig;
import com.c4_soft.springaddons.security.oauth2.test.AuthenticationFactoriesTestConf;
import com.c4_soft.springaddons.security.oauth2.test.annotations.WithJwt;
import com.c4_soft.springaddons.security.oauth2.test.annotations.parameterized.ParameterizedAuthentication;

@Import({ MessageService.class, SecurityConfig.class })
@ImportAutoConfiguration(AuthenticationFactoriesTestConf.class)
@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class MessageServiceUnitTest {
    @Autowired
    MessageService messageService;

    @Autowired
    WithJwt.AuthenticationFactory authFactory;
    
    @MockBean
    ReactiveJwtDecoder jwtDecoder;

    /*----------------------------------------------------------------------------*/
    /* greet()                                                                    */
    /* Expects a JwtAuthenticationToken to be retrieved from the security-context */
    /*----------------------------------------------------------------------------*/

    @Test
    void givenSecurityContextIsEmpty_whenGreet_thenThrowsAuthenticationCredentialsNotFoundException() {
        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> messageService.greet()
            .block());
    }

    @Test
    @WithAnonymousUser
    void givenUserIsAnonymous_whenGreet_thenThrowsClassCastException() {
        assertThrows(ClassCastException.class, () -> messageService.greet()
            .block());
    }

    @ParameterizedTest
    @MethodSource("allIdentities")
    void givenUserIsAuthenticated_whenGreet_thenReturnGreetingWithPreferredUsernameAndAuthorities(@ParameterizedAuthentication Authentication auth) {
        final var jwt = (JwtAuthenticationToken) auth;
        final var expected = "Hello %s! You are granted with %s.".formatted(jwt.getTokenAttributes().get(StandardClaimNames.PREFERRED_USERNAME), auth.getAuthorities());
        assertEquals(expected, messageService.greet()
            .block());
    }

    @Test
    @WithMockUser(authorities = { "admin", "ROLE_AUTHORIZED_PERSONNEL" }, username = "ch4mpy")
    void givenSecurityContextIsPopulatedWithUsernamePasswordAuthenticationToken_whenGreet_thenThrowsClassCastException() {
        assertThrows(ClassCastException.class, () -> messageService.greet()
            .block());
    }

    /*--------------------------------------------------------------------*/
    /* getSecret()                                                        */
    /* is secured with "@PreAuthorize("hasRole('AUTHORIZED_PERSONNEL')")" */
    /*--------------------------------------------------------------------*/

    @Test
    @WithAnonymousUser
    void givenUserIsAnonymous_whenGetSecret_thenThrowsAccessDeniedException() {
        assertThrows(AccessDeniedException.class, () -> messageService.getSecret()
            .block());
    }

    @Test
    @WithJwt("ch4mpy.json")
    void givenUserIsCh4mpy_whenGetSecret_thenReturnSecret() {
        assertEquals("Only authorized personnel can read that", messageService.getSecret()
            .block());
    }

    @Test
    @WithJwt("tonton-pirate.json")
    void givenUserIsTontonPirate_whenGetSecret_thenThrowsAccessDeniedException() {
        assertThrows(AccessDeniedException.class, () -> messageService.getSecret()
            .block());
    }

    /*--------------------------------------------*/
    /* methodSource returning all test identities */
    /*--------------------------------------------*/
    private Stream<AbstractAuthenticationToken> allIdentities() {
        final var authentications = authFactory.authenticationsFrom("ch4mpy.json", "tonton-pirate.json").toList();
        return authentications.stream();
    }

}
