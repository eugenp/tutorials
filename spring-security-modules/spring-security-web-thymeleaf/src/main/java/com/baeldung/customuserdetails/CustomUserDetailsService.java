package com.baeldung.customuserdetails;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final Map<String, CustomUserDetails> userRegistry = new HashMap<>();

    public CustomUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        userRegistry.put("user", new CustomUserDetails.Builder().withFirstName("Mark")
            .withLastName("Johnson")
            .withEmail("mark.johnson@email.com")
            .withUsername("user")
            .withPassword(passwordEncoder.encode("password"))
            .withAuthorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
            .build());
        userRegistry.put("admin", new CustomUserDetails.Builder().withFirstName("James")
            .withLastName("Davis")
            .withEmail("james.davis@email.com")
            .withUsername("admin")
            .withPassword(passwordEncoder.encode("admin"))
            .withAuthorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
            .build());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final CustomUserDetails userDetails = userRegistry.get(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException(username);
        }
        return userDetails;
    }
}
