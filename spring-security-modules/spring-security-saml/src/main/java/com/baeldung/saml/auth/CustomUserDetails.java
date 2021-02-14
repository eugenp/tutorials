package com.baeldung.saml.auth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUserDetails extends User {

    private final AuthMethod authMethod;

    public CustomUserDetails(AuthMethod authMethod,
        String username,
        String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.authMethod = authMethod;
    }

    public CustomUserDetails(AuthMethod authMethod,
        String username,
        String password,
        boolean enabled,
        boolean accountNonExpired,
        boolean credentialsNonExpired,
        boolean accountNonLocked,
        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.authMethod = authMethod;
    }
}
