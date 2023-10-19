package com.baeldung.cassecuredapp.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.baeldung.cassecuredapp.user.User;
import com.baeldung.cassecuredapp.user.UserRepository;

public class CasUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Get the user from the database.
        User user = getUserFromDatabase(username);

        // Create a UserDetails object.
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
           Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        return userDetails;
    }

    private User getUserFromDatabase(String username) {
       return userRepository.findByEmail(username);
    }
}
