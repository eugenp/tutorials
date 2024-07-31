package com.baeldung.chainofresponsibility;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ChainOfResponsibilityIntegrationTest {

    private static AuthenticationProcessor getChainOfAuthProcessor() {

        AuthenticationProcessor oAuthProcessor = new OAuthAuthenticationProcessor(null);
        AuthenticationProcessor unamePasswordProcessor = new UsernamePasswordAuthenticationProcessor(oAuthProcessor);
        return unamePasswordProcessor;
    }

    @Test
    public void givenOAuthProvider_whenCheckingAuthorized_thenSuccess() {
        AuthenticationProcessor authProcessorChain = getChainOfAuthProcessor();
        boolean isAuthorized = authProcessorChain.isAuthorized(new OAuthTokenProvider());
        assertTrue(isAuthorized);
    }

    @Test
    public void givenUsernamePasswordProvider_whenCheckingAuthorized_thenSuccess() {
        AuthenticationProcessor authProcessorChain = getChainOfAuthProcessor();
        boolean isAuthorized = authProcessorChain.isAuthorized(new UsernamePasswordProvider());
        assertTrue(isAuthorized);
    }

    @Test
    public void givenSamlAuthProvider_whenCheckingAuthorized_thenFailure() {
        AuthenticationProcessor authProcessorChain = getChainOfAuthProcessor();
        boolean isAuthorized = authProcessorChain.isAuthorized(new SamlAuthenticationProvider());
        assertTrue(!isAuthorized);
    }

}
