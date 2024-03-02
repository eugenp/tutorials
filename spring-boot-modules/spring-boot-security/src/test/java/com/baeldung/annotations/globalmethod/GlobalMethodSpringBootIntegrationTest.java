package com.baeldung.annotations.globalmethod;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class GlobalMethodSpringBootIntegrationTest {

    public static final String HELLO_JSR_250 = "Hello Jsr250";
    public static final String HELLO_PUBLIC = "Hello Public";
    public static final String HELLO_PRE_AUTHORIZE = "Hello PreAuthorize";
    public static final String PUBLIC_RESOURCE = "/hello/baeldung.txt";
    public static final String HELLO_FROM_PUBLIC_RESOURCE = "Hello From Baeldung";
    private static final String PROTECTED_METHOD = "/protected";

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private AnnotationSecuredController api;

    @WithMockUser(username = "baeldung", roles = "USER")
    @Test
    void givenUserWithRole_whenJsr250_thenOk() {
        assertThat(api.jsr250Hello()).isEqualTo(HELLO_JSR_250);
    }

    @WithMockUser(username = "baeldung", roles = "NOT-USER")
    @Test
    void givenWrongRole_whenJsr250_thenAccessDenied() {
        assertThrows(AccessDeniedException.class, () -> api.jsr250Hello());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_whenPublic_thenOk() {
        assertThat(api.publicHello()).isEqualTo(HELLO_PUBLIC);
    }

    @Test()
    @WithAnonymousUser
    void givenAnonymousUser_whenJsr250_thenAccessDenied() {
        assertThrows(AccessDeniedException.class, () -> api.jsr250Hello());
    }

    // Tests for indirect calling of method
    @Test
    @WithAnonymousUser
    void givenAnonymousUser_whenIndirectCall_thenNoSecurity() {
        assertThat(api.indirectHello()).isEqualTo(HELLO_JSR_250);
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_whenIndirectToDifferentClass_thenAccessDenied() {
        assertThrows(AccessDeniedException.class, () -> api.differentClassHello());
    }

    // Tests for static resource
    @Test
    void givenPublicResource_whenGetViaWeb_thenOk() {
        ResponseEntity<String> result = template.getForEntity(PUBLIC_RESOURCE, String.class);
        assertEquals(HELLO_FROM_PUBLIC_RESOURCE, result.getBody());
    }

    @Test
    void givenProtectedMethod_whenGetViaWeb_thenRedirectToLogin() {
        ResponseEntity<String> result = template.getForEntity(PROTECTED_METHOD, String.class);
        assertThat(result.getBody()).contains("Please sign in");
    }

    // Tests for preAuthorize annotations
    @WithMockUser(username = "baeldung", roles = "USER")
    @Test
    void givenUserWithRole_whenCallPreAuthorize_thenOk() {
        assertThat(api.preAuthorizeHello()).isEqualTo(HELLO_PRE_AUTHORIZE);
    }

    @WithMockUser(username = "baeldung", roles = "NOT-USER")
    @Test
    void givenWrongRole_whenCallPreAuthorize_thenAccessDenied() {
        assertThrows(AccessDeniedException.class, () -> api.preAuthorizeHello());
    }

    @Test
    @WithAnonymousUser
    void givenAnonymousUser_whenCallPreAuthorize_thenAccessDenied() {
        assertThrows(AccessDeniedException.class, () -> api.preAuthorizeHello());
    }

}
