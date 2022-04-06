package com.baeldung.loginextrafieldssimple;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public class SimpleUserRepository implements UserRepository {

    private PasswordEncoder passwordEncoder;
    
    public SimpleUserRepository(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUser(String username, String domain) {
        if (StringUtils.isAnyBlank(username, domain)) {
            return null;
        } else {
            Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
            User user =  new User(username, domain, 
                passwordEncoder.encode("secret"), true, 
                true, true, true, authorities);
            return user;
        }
    }

}
