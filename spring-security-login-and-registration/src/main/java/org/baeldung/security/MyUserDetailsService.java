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
import org.baeldung.persistence.dao.VerificationTokenRepository;
import org.baeldung.persistence.model.User;
import org.baeldung.persistence.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private UserRepository userRepository;
    @Autowired
    private IUserService service;
    @Autowired
    private MessageSource messages;
    @Autowired
    private VerificationTokenRepository tokenRepository;;

    @Autowired
    public MyUserDetailsService(UserRepository repository) {
        this.userRepository = repository;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        try {
            LOGGER.debug("Loading user by username: {}", email);
            User user = userRepository.findByEmail(email);
            LOGGER.debug("Found user: {}", user);
            if (user == null) {
                return new org.springframework.security.core.userdetails.User(" ", " ", enabled, true, true, true, getAuthorities(new Integer(1)));
            }
            if (!user.isEnabled()) {
                accountNonExpired = false;
                service.deleteUser(user);
                return new org.springframework.security.core.userdetails.User(" ", " ", enabled, accountNonExpired, true, true, getAuthorities(new Integer(1)));
            }
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword().toLowerCase(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, getAuthorities(user.getRole().getRole()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }

    public List<String> getRoles(Integer role) {
        List<String> roles = new ArrayList<String>();
        if (role.intValue() == 2) {
            roles.add("ROLE_ADMIN");
        } else if (role.intValue() == 1) {
            roles.add("ROLE_USER");
        }
        return roles;
    }

    private static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
