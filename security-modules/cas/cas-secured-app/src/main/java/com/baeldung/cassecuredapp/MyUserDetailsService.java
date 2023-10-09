package com.baeldung.cassecuredapp;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Get the user from the database.
        Users users = getUserFromDatabase(username);

        // Create a UserDetails object.
        UserDetails userDetails = new User(
            users.getEmail(),
            users.getPassword(),
           Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));

        return userDetails;
    }

    private Users getUserFromDatabase(String username) {
       return userRepository.findByEmail(username);
    }
}
