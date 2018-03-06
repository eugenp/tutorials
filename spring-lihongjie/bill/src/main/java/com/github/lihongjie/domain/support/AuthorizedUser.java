package com.github.lihongjie.domain.support;

import com.github.lihongjie.domain.SecurityGroup;
import com.github.lihongjie.domain.SecurityPermission;
import com.github.lihongjie.domain.User;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by lihongjie on 7/19/17.
 *
 */
public class AuthorizedUser extends User implements UserDetails {

    private final Collection<GrantedAuthority> authorities;

    public AuthorizedUser(User user) {

        BeanUtils.copyProperties(user, this);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for(SecurityGroup securityGroup : user.getSecurityGroups()) {
            for (SecurityPermission permission : securityGroup.getSecurityPermissions()) {
                authorities.add(new SimpleGrantedAuthority(permission.getDescription()));
            }
        }
        this.authorities = authorities;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return getCurrentPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
