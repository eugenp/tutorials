package org.baeldung.web;

import static org.junit.Assert.assertEquals;

import org.baeldung.config.MvcConfig;
import org.baeldung.config.PersistenceConfig;
import org.baeldung.config.SecurityConfig;
import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MvcConfig.class, SecurityConfig.class, PersistenceConfig.class })
@WebAppConfiguration
public class CustomUserDetailsServiceTest {

    public static final String USERNAME = "user";
    public static final String PASSWORD = "pass";
    public static final String USERNAME2 = "user2";

    @Autowired
    UserRepository myUserRepository;

    @Autowired
    AuthenticationProvider authenticationProvider;
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void givenExistingUser_whenAuthenticate_thenRetrieveFromDb() {
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(passwordEncoder.encode(PASSWORD));

        myUserRepository.save(user);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD);
        Authentication authentication = authenticationProvider.authenticate(auth);

        assertEquals(authentication.getName(), USERNAME);
    }

    @Test(expected = BadCredentialsException.class)
    public void givenIncorrectUser_whenAuthenticate_thenBadCredentialsException() {
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(passwordEncoder.encode(PASSWORD));

        myUserRepository.save(user);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(USERNAME2, PASSWORD);
        authenticationProvider.authenticate(auth);
    }
    
    @After
    public void tearDown() {
        myUserRepository.removeUserByUsername(USERNAME);
    }

}
