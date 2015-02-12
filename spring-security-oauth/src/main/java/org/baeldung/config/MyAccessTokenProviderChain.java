package org.baeldung.config;

import java.util.Collections;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.OAuth2AccessTokenSupport;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public class MyAccessTokenProviderChain extends OAuth2AccessTokenSupport implements AccessTokenProvider {

    private final List<AccessTokenProvider> chain;

    private ClientTokenServices clientTokenServices;

    public MyAccessTokenProviderChain(List<? extends AccessTokenProvider> chain) {
        this.chain = chain == null ? Collections.<AccessTokenProvider> emptyList() : Collections.unmodifiableList(chain);
    }

    public void setClientTokenServices(ClientTokenServices clientTokenServices) {
        this.clientTokenServices = clientTokenServices;
    }

    public boolean supportsResource(OAuth2ProtectedResourceDetails resource) {
        for (AccessTokenProvider tokenProvider : chain) {
            if (tokenProvider.supportsResource(resource)) {
                return true;
            }
        }
        return false;
    }

    public boolean supportsRefresh(OAuth2ProtectedResourceDetails resource) {
        for (AccessTokenProvider tokenProvider : chain) {
            if (tokenProvider.supportsRefresh(resource)) {
                return true;
            }
        }
        return false;
    }

    public OAuth2AccessToken obtainAccessToken(OAuth2ProtectedResourceDetails resource, AccessTokenRequest request) throws UserRedirectRequiredException, AccessDeniedException {
        System.out.println("Obtain new token=====");
        OAuth2AccessToken accessToken = null;
        OAuth2AccessToken existingToken = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("The authentication is ---- " + auth);
        if (auth instanceof AnonymousAuthenticationToken) {
            if (!resource.isClientOnly()) {
                throw new InsufficientAuthenticationException("Authentication is required to obtain an access token (anonymous not allowed)");
            }
        }

        if (resource.isClientOnly() || (auth != null && auth.isAuthenticated())) {
            existingToken = request.getExistingToken();
            System.out.println("checking existing token =====");
            if (existingToken == null && clientTokenServices != null) {
                System.out.println("get existing token from clientTokenServices ==== ");
                existingToken = clientTokenServices.getAccessToken(resource, auth);
            }

            if (existingToken != null) {
                if (existingToken.isExpired()) {
                    System.out.println("expired token");
                    if (clientTokenServices != null) {
                        clientTokenServices.removeAccessToken(resource, auth);
                    }
                    OAuth2RefreshToken refreshToken = existingToken.getRefreshToken();
                    if (refreshToken != null) {
                        System.out.println("let's refresh it");
                        accessToken = refreshAccessToken(resource, refreshToken, request);
                    }
                } else {
                    System.out.println("use existing because not expired yet");
                    accessToken = existingToken;
                }
            }
        }

        if (accessToken == null) {
            System.out.println("no token so let get it");
            accessToken = obtainNewAccessTokenInternal(resource, request);

            if (accessToken == null) {
                throw new IllegalStateException("An OAuth 2 access token must be obtained or an exception thrown.");
            }
        }

        if (clientTokenServices != null && auth != null && auth.isAuthenticated()) {
            clientTokenServices.saveAccessToken(resource, auth, accessToken);
        }

        return accessToken;
    }

    protected OAuth2AccessToken obtainNewAccessTokenInternal(OAuth2ProtectedResourceDetails details, AccessTokenRequest request) throws UserRedirectRequiredException, AccessDeniedException {

        if (request.isError()) {
            throw OAuth2Exception.valueOf(request.toSingleValueMap());
        }

        for (AccessTokenProvider tokenProvider : chain) {
            if (tokenProvider.supportsResource(details)) {
                System.out.println("we will use this provider to get it => " + tokenProvider.getClass().getName());
                return tokenProvider.obtainAccessToken(details, request);
            }
        }

        throw new OAuth2AccessDeniedException("Unable to obtain a new access token for resource '" + details.getId() + "'. The provider manager is not configured to support it.", details);
    }

    public OAuth2AccessToken refreshAccessToken(OAuth2ProtectedResourceDetails resource, OAuth2RefreshToken refreshToken, AccessTokenRequest request) throws UserRedirectRequiredException {
        for (AccessTokenProvider tokenProvider : chain) {
            if (tokenProvider.supportsRefresh(resource)) {
                System.out.println("we will use this provider to refresh it => " + tokenProvider.getClass().getName());
                return tokenProvider.refreshAccessToken(resource, refreshToken, request);
            }
        }
        throw new OAuth2AccessDeniedException("Unable to obtain a new access token for resource '" + resource.getId() + "'. The provider manager is not configured to support it.", resource);
    }

}