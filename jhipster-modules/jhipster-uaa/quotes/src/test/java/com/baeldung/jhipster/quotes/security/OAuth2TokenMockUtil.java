package com.baeldung.jhipster.quotes.security;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;

/**
 * A bean providing simple mocking of OAuth2 access tokens for security integration tests.
 */
@Component
public class OAuth2TokenMockUtil {

    @MockBean
    private ResourceServerTokenServices tokenServices;

    private OAuth2Authentication createAuthentication(String username, Set<String> scopes, Set<String> roles) {
        List<GrantedAuthority> authorities = roles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        User principal = new User(username, "test", true, true, true, true, authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(),
            principal.getAuthorities());

        // Create the authorization request and OAuth2Authentication object
        OAuth2Request authRequest = new OAuth2Request(null, "testClient", null, true, scopes, null, null, null,
            null);
        return new OAuth2Authentication(authRequest, authentication);
    }

    public RequestPostProcessor oauth2Authentication(String username, Set<String> scopes, Set<String> roles) {
        String uuid = String.valueOf(UUID.randomUUID());

        given(tokenServices.loadAuthentication(uuid))
            .willReturn(createAuthentication(username, scopes, roles));

        given(tokenServices.readAccessToken(uuid)).willReturn(new DefaultOAuth2AccessToken(uuid));

        return new OAuth2PostProcessor(uuid);
    }

    public RequestPostProcessor oauth2Authentication(String username, Set<String> scopes) {
        return oauth2Authentication(username, scopes, Collections.emptySet());
    }

    public RequestPostProcessor oauth2Authentication(String username) {
        return oauth2Authentication(username, Collections.emptySet());
    }

    public static class OAuth2PostProcessor implements RequestPostProcessor {

        private String token;

        public OAuth2PostProcessor(String token) {
            this.token = token;
        }

        @Override
        public MockHttpServletRequest postProcessRequest(MockHttpServletRequest mockHttpServletRequest) {
            mockHttpServletRequest.addHeader("Authorization", "Bearer " + token);

            return mockHttpServletRequest;
        }
    }
}
