package com.baeldung.apikeysecretauth.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import com.baeldung.apikeysecretauth.model.User;

import lombok.Getter;

@Getter
public class ApiKeySecretAuthenticationToken extends AbstractAuthenticationToken {
    private final String apiKey;
    private final String apiSecret;
    private final User principal;

    public ApiKeySecretAuthenticationToken(String apiKey, String apiSecret) {
        super(null);
        this.principal = null;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        setAuthenticated(false);
    }

    public ApiKeySecretAuthenticationToken(User principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.apiKey = "";
        this.apiSecret = "";
        super.setAuthenticated(true);
    }

    @Override
    public String getCredentials() {
        return String.format("%s:%s", this.apiKey, this.apiSecret);
    }

    @Override
    public User getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        Assert.isTrue(!authenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }
}
