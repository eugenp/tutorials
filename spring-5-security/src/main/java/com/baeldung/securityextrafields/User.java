package com.baeldung.securityextrafields;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class User extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

    private final String domain;

    public User(String username, String domain, String password, boolean enabled, 
        boolean accountNonExpired, boolean credentialsNonExpired, 
        boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }
}
