package org.baeldung.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.baeldung.security.SecurityRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final Log logger = LogFactory.getLog(this.getClass());

    private final Map<String, User> availableUsers = new HashMap<String, User>();

    public MyUserDetailsService() {
        populateDemoUsers();
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        logger.info("Load user by username " + username);

        final UserDetails user = availableUsers.get(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return user;
    }

    /**
     * Create demo users (note: obviously in a real system these would be persisted
     * in database or retrieved from another system).
     */
    private void populateDemoUsers() {
        logger.info("Populate demo users");

        availableUsers.put("user", createUser("user", "password", Arrays.asList(SecurityRole.ROLE_USER)));
        availableUsers.put("admin", createUser("admin", "password", Arrays.asList(SecurityRole.ROLE_ADMIN)));
    }

    private User createUser(final String username, final String password, final List<SecurityRole> roles) {
        logger.info("Create user " + username);

        final List<GrantedAuthority> authorities = roles.stream().map(role -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toList());

        return new User(username, password, true, true, true, true, authorities);
    }
}
