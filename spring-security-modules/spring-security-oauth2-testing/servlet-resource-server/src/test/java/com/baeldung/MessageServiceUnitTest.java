package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.ServletResourceServerApplication.MessageService;
import com.c4_soft.springaddons.security.oauth2.test.annotations.OpenIdClaims;
import com.c4_soft.springaddons.security.oauth2.test.annotations.WithMockJwtAuth;

@Import({ MessageService.class })
@ExtendWith(SpringExtension.class)
@EnableMethodSecurity
class MessageServiceUnitTest {
    @Autowired
    MessageService messageService;

    /*----------------------------------------------------------------------------*/
    /* greet()                                                                    */
    /* Expects a JwtAuthenticationToken to be retrieved from the security-context */
    /*----------------------------------------------------------------------------*/

    @Test
    void givenSecurityContextIsNotSet_whenGreet_thenThrowsAuthenticationCredentialsNotFoundException() {
        assertThrows(AuthenticationCredentialsNotFoundException.class, () -> messageService.getSecret());
    }

    @Test
    @WithAnonymousUser
    void givenUserIsAnonymous_whenGreet_thenThrowsAccessDeniedException() {
        assertThrows(AccessDeniedException.class, () -> messageService.getSecret());
    }

    @Test
    @WithMockJwtAuth(authorities = { "admin", "ROLE_AUTHORIZED_PERSONNEL" }, claims = @OpenIdClaims(preferredUsername = "ch4mpy"))
    void givenSecurityContextIsPopulatedWithJwtAuthenticationToken_whenGreet_thenReturnGreetingWithPreferredUsernameAndAuthorities() {
        assertEquals("Hello ch4mpy! You are granted with [admin, ROLE_AUTHORIZED_PERSONNEL].", messageService.greet());
    }

    @Test
    @WithMockUser(authorities = { "admin", "ROLE_AUTHORIZED_PERSONNEL" }, username = "ch4mpy")
    void givenSecurityContextIsPopulatedWithUsernamePasswordAuthenticationToken_whenGreet_thenThrowsClassCastException() {
        assertThrows(ClassCastException.class, () -> messageService.greet());
    }

    /*--------------------------------------------------------------------*/
    /* getSecret()                                                        */
    /* is secured with "@PreAuthorize("hasRole('AUTHORIZED_PERSONNEL')")" */
    /*--------------------------------------------------------------------*/

    @Test
    @WithAnonymousUser
    void givenUserIsAnonymous_whenGetSecret_thenThrowsAccessDeniedException() {
        assertThrows(AccessDeniedException.class, () -> messageService.getSecret());
    }

    @Test
    @WithMockJwtAuth(authorities = { "admin", "ROLE_AUTHORIZED_PERSONNEL" }, claims = @OpenIdClaims(preferredUsername = "ch4mpy"))
    void givenUserIsGrantedWithRoleAuthorizedPersonnel_whenGetSecret_thenReturnSecret() {
        assertEquals("Only authorized personnel can read that", messageService.getSecret());
    }

    @Test
    @WithMockJwtAuth(authorities = { "admin" }, claims = @OpenIdClaims(preferredUsername = "ch4mpy"))
    void givenUserIsNotGrantedWithRoleAuthorizedPersonnel_whenGetSecret_thenThrowsAccessDeniedException() {
        assertThrows(AccessDeniedException.class, () -> messageService.getSecret());
    }

}
