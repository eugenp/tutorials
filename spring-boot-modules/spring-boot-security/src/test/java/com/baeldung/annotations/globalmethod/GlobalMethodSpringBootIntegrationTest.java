package com.baeldung.annotations.globalmethod;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
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

    @WithMockUser(username="baeldung", roles = "USER")
    @Test
    public void givenUserWithRole_whenJsr250_thenOk() {
        assertThat(api.jsr250Hello()).isEqualTo(HELLO_JSR_250);
    }

    @WithMockUser(username="baeldung", roles = "NOT-USER")
    @Test(expected = AccessDeniedException.class)
    public void givenWrongRole_whenJsr250_thenAccessDenied() {
        api.jsr250Hello();
    }

    @Test
    @WithAnonymousUser
    public void givenAnonymousUser_whenPublic_thenOk() {
        assertThat(api.publicHello()).isEqualTo(HELLO_PUBLIC);
    }

    @Test(expected = AccessDeniedException.class)
    @WithAnonymousUser
    public void givenAnonymousUser_whenJsr250_thenAccessDenied() {
        api.jsr250Hello();
    }

    // Tests for indirect calling of method
    @Test
    @WithAnonymousUser
    public void givenAnonymousUser_whenIndirectCall_thenNoSecurity() {
        assertThat(api.indirectHello()).isEqualTo(HELLO_JSR_250);
    }

    @Test(expected = AccessDeniedException.class)
    @WithAnonymousUser
    public void givenAnonymousUser_whenIndirectToDifferentClass_thenAccessDenied() {
        api.differentClassHello();
    }

    // Tests for static resource
    @Test
    public void givenPublicResource_whenGetViaWeb_thenOk() {
        ResponseEntity<String> result = template.getForEntity(PUBLIC_RESOURCE, String.class);
        assertEquals(HELLO_FROM_PUBLIC_RESOURCE, result.getBody());
    }

    @Test
    public void givenProtectedMethod_whenGetViaWeb_thenRedirectToLogin() {
        ResponseEntity<String> result = template.getForEntity(PROTECTED_METHOD, String.class);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
    }

    // Tests for preAuthorize annotations
    @WithMockUser(username="baeldung", roles = "USER")
    @Test
    public void givenUserWithRole_whenCallPreAuthorize_thenOk() {
        assertThat(api.preAuthorizeHello()).isEqualTo(HELLO_PRE_AUTHORIZE);
    }

    @WithMockUser(username="baeldung", roles = "NOT-USER")
    @Test(expected = AccessDeniedException.class)
    public void givenWrongRole_whenCallPreAuthorize_thenAccessDenied() {
        api.preAuthorizeHello();
    }

    @Test(expected = AccessDeniedException.class)
    @WithAnonymousUser
    public void givenAnonymousUser_whenCallPreAuthorize_thenAccessDenied() {
        api.preAuthorizeHello();
    }

}
