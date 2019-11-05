package com.baeldung.springsecuredsockets.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

    Logger log = LoggerFactory.getLogger(CustomDaoAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails u = null;

        try {
            u = getUserDetailsService().loadUserByUsername(name);
        } catch (UsernameNotFoundException ex) {
            log.error("User '" + name + "' not found");
        } catch (Exception e) {
            log.error("Exception in CustomDaoAuthenticationProvider: " + e);
        }

        if (u != null) {
            if (u.getPassword().equals(password)) {
                return new UsernamePasswordAuthenticationToken(u, password, u.getAuthorities());
            }
        }

        throw new BadCredentialsException(messages.getMessage("CustomDaoAuthenticationProvider.badCredentials", "Bad credentials"));

    }
}
