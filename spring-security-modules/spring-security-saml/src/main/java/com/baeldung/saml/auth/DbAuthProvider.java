package com.baeldung.saml.auth;

import com.baeldung.saml.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Provides authentication for "database" users, i.e. users whose credentials
 * are authenticated against the database instead of Okta/SAML
 * @author jcavazos
 */
@Component
public class DbAuthProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbAuthProvider.class);

    private final CombinedUserDetailsService combinedUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DbAuthProvider(CombinedUserDetailsService combinedUserDetailsService, PasswordEncoder passwordEncoder) {
        this.combinedUserDetailsService = combinedUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!StringUtils.endsWithIgnoreCase(authentication.getPrincipal().toString(), Constants.DB_USERNAME_SUFFIX)) {
            // this user is not supported by DB authentication
            return null;
        }

        UserDetails user = combinedUserDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        String rawPw = authentication.getCredentials() == null ? null : authentication.getCredentials().toString();

        if (passwordEncoder.matches(rawPw, user.getPassword())) {
            LOGGER.warn("User successfully logged in: {}", user.getUsername());
            return new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    rawPw,
                    Collections.emptyList());
        } else {
            LOGGER.error("User failed to log in: {}", user.getUsername());
            throw new BadCredentialsException("Bad password");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }
}
