package com.baeldung.mcp.oauth2authorizationserver.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootTest(classes = AuthorizationServerConfig.class)
class AuthorizationServerConfigTest {

    private final ApplicationContext context;

    private final RegisteredClientRepository registeredClientRepository;

    private final AuthorizationServerSettings authorizationServerSettings;

    public AuthorizationServerConfigTest(ApplicationContext context, RegisteredClientRepository registeredClientRepository,
        AuthorizationServerSettings authorizationServerSettings) {
        this.authorizationServerSettings = authorizationServerSettings;
        this.registeredClientRepository = registeredClientRepository;
        this.context = context;
    }

    @Test
    void givenContext_whenLoaded_thenSecurityFilterChainsPresent() {
        SecurityFilterChain chain1 = (SecurityFilterChain) context.getBean("authorizationServerSecurityFilterChain");
        SecurityFilterChain chain2 = (SecurityFilterChain) context.getBean("defaultSecurityFilterChain");
        assertNotNull(chain1);
        assertNotNull(chain2);
    }

    @Test
    void givenRegisteredClientRepository_whenQueried_thenContainsExpectedClient() {
        RegisteredClient client = registeredClientRepository.findByClientId("mcp-client");
        assertNotNull(client);
        assertEquals("mcp-client", client.getClientId());
        assertTrue(client.getClientAuthenticationMethods()
            .stream()
            .anyMatch(m -> m.getValue()
                .equals("client_secret_basic")));
        assertTrue(client.getAuthorizationGrantTypes()
            .stream()
            .anyMatch(g -> g.getValue()
                .equals("authorization_code")));
        assertTrue(client.getScopes()
            .contains("mcp.read"));
        assertTrue(client.getScopes()
            .contains("mcp.write"));
    }

    @Test
    void givenAuthorizationServerSettings_whenLoaded_thenIssuerIsCorrect() {
        assertEquals("http://localhost:9000", authorizationServerSettings.getIssuer());
    }
}

