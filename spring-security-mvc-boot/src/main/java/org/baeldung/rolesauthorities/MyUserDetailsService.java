package org.baeldung.rolesauthorities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.baeldung.rolesauthorities.model.Role;
import org.baeldung.rolesauthorities.model.User;
import org.baeldung.rolesauthorities.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public MyUserDetailsService() {
        super();
    }

    // API

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("No user found with username: " + email);
            }
            org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities(user.getRoles()));
            return userDetails;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    // UTIL

    private final Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
    	List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    	for (Role role: roles) {
    		authorities.add(new SimpleGrantedAuthority(role.getName()));
    		authorities.addAll(role.getPrivileges()
    				.stream()
    				.map(p -> new SimpleGrantedAuthority(p.getName()))
    				.collect(Collectors.toList()));
    	}
        return authorities;
    }
}
