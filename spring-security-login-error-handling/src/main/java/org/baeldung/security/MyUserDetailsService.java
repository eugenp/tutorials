package org.baeldung.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

   // @Autowired
   // public MyUserDetailsService(UserRepository repository) {
     //   this.userRepository = repository;
   // }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            LOGGER.debug("Loading user by username: {}", email);
            User user = userRepository.findByEmail(email);
            LOGGER.debug("Found user: {}", user);
            if (user == null) {
                // throw new UsernameNotFoundException("No user found with username: " + username);
                boolean enabled = false;
                return new org.springframework.security.core.userdetails.User(" ", " ", enabled, true, true, true, getAuthorities(new Integer(1)));
            }
            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword().toLowerCase(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthorities(user.getRole().getRole()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }

    public List<String> getRoles(Integer role) {
        List<String> roles = new ArrayList<String>();

        if (role.intValue() == 2) {
            // roles.add("ROLE_USER");
            roles.add("ROLE_ADMIN");

        } else if (role.intValue() == 1) {
            roles.add("ROLE_USER");
        }

        return roles;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}
