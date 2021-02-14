package com.baeldung.saml.auth;

import com.baeldung.saml.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;

/**
 * Supplies {@link UserDetails} information for both DB and SAML users
 * @author jcavazos
 */
@Service
public class CombinedUserDetailsService implements UserDetailsService, SAMLUserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CombinedUserDetailsService.class);

    private final UserRepository userRepository;

    @Autowired
    public CombinedUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        StoredUser storedUser = lookupUser(s);
        return new CustomUserDetails(
                AuthMethod.DATABASE,
                storedUser.getUsername(),
                storedUser.getPasswordHash(),
                new LinkedList<>());
    }

    @Override
    public Object loadUserBySAML(SAMLCredential credential) throws UsernameNotFoundException {
        LOGGER.info("Loading UserDetails by SAMLCredentials: {}", credential.getNameID());
        StoredUser storedUser = lookupUser(credential.getNameID().getValue());
        return new CustomUserDetails(
                AuthMethod.SAML,
                storedUser.getUsername(),
                storedUser.getPasswordHash(),
                new LinkedList<>());
    }

    private StoredUser lookupUser(String username) {
        LOGGER.info("Loading UserDetails by username: {}", username);

        Optional<StoredUser> user = userRepository.findByUsernameIgnoreCase(username);

        if (!user.isPresent()) {
            LOGGER.error("User not found in database: {}", user);
            throw new UsernameNotFoundException(username);
        }

        return user.get();
    }
}
