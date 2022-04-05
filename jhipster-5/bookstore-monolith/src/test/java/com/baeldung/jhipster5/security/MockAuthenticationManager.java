package com.baeldung.jhipster5.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

/**
 * AuthenticationManager used solely by unit tests.
 */
@Component
@Primary
public class MockAuthenticationManager implements AuthenticationManager
{
    private final static Collection<? extends GrantedAuthority> ROLES =
        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {

        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());

        if(userDetails == null || !passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword()))
        {
            throw new BadCredentialsException("Invalid username/password");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
            authentication.getPrincipal().toString(),
            authentication.getCredentials().toString(),
            ROLES);

        return token;
    }
}
