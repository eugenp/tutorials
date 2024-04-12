package com.baeldung.enablemethodsecurity.user;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser implements UserDetails {
    private String userName;
    private String password;
    private List<GrantedAuthority> grantedAuthorityList;
    private boolean accessToRestrictedPolicy;

    public static SecurityUser builder() {
        return new SecurityUser();
    }

    public SecurityUser withAccessToRestrictedPolicy(boolean restrictedPolicy) {
        this.accessToRestrictedPolicy = restrictedPolicy;
        return this;
    }

    public boolean hasAccessToRestrictedPolicy() {
        return accessToRestrictedPolicy;
    }

    public SecurityUser withGrantedAuthorityList(List<GrantedAuthority> grantedAuthorityList) {
        this.grantedAuthorityList = grantedAuthorityList;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorityList;
    }

    public SecurityUser withPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public SecurityUser withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
