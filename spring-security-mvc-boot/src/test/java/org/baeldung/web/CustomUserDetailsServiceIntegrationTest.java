package org.baeldung.web;

import static org.junit.Assert.assertEquals;

import org.baeldung.config.MvcConfig;
import org.baeldung.config.PersistenceConfig;
import org.baeldung.config.SecurityConfig;
import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.model.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MvcConfig.class, SecurityConfig.class, PersistenceConfig.class})
@WebAppConfiguration
public class CustomUserDetailsServiceIntegrationTest {

    private static final String USERNAME = "user";
    private static final String PASSWORD = "pass";
    private static final String USERNAME2 = "user2";

    @Autowired
    private UserRepository myUserRepository;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //

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

    //

    @After
    public void tearDown() {
        myUserRepository.removeUserByUsername(USERNAME);
    }

}
