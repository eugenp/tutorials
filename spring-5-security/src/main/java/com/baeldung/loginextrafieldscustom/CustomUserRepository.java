package com.baeldung.loginextrafieldscustom;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public class CustomUserRepository implements UserRepository {

    @Override
    public User findUser(String username, String domain) {
        if (StringUtils.isAnyBlank(username, domain)) {
            return null;
        } else {
            Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
            User user =  new User(username, domain, 
                "$2a$10$U3GhSMpsMSOE8Kqsbn58/edxDBKlVuYMh7qk/7ErApYFjJzi2VG5K", true, 
                true, true, true, authorities);
            return user;
        }
    }

}
