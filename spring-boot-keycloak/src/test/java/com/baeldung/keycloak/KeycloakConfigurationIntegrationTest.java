package com.baeldung.keycloak;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springboot.client.KeycloakSecurityContextClientRequestInterceptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBoot.class)
public class KeycloakConfigurationIntegrationTest {

    @Spy
    private KeycloakSecurityContextClientRequestInterceptor factory;

    private MockHttpServletRequest servletRequest;

    @Mock
    public KeycloakSecurityContext keycloakSecurityContext;

    @Mock
    private KeycloakPrincipal keycloakPrincipal;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        servletRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(servletRequest));
        servletRequest.setUserPrincipal(keycloakPrincipal);
        when(keycloakPrincipal.getKeycloakSecurityContext()).thenReturn(keycloakSecurityContext);
    }

    @Test
    public void testGetKeycloakSecurityContext() throws Exception {
        assertNotNull(keycloakPrincipal.getKeycloakSecurityContext());
    }

}
