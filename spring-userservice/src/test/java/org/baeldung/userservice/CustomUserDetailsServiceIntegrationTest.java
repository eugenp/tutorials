package org.baeldung.userservice;

import static org.junit.Assert.assertEquals;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.baeldung.custom.config.MvcConfig;
import org.baeldung.custom.config.PersistenceDerbyJPAConfig;
import org.baeldung.custom.config.SecSecurityConfig;
import org.baeldung.user.service.MyUserService;
import org.baeldung.web.MyUserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MvcConfig.class, PersistenceDerbyJPAConfig.class, SecSecurityConfig.class })
@WebAppConfiguration
public class CustomUserDetailsServiceIntegrationTest {

    private static final Logger LOG = Logger.getLogger("CustomUserDetailsServiceTest");

    public static final String USERNAME = "user";
    public static final String PASSWORD = "pass";
    public static final String USERNAME2 = "user2";

    @Autowired
    MyUserService myUserService;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Test
    public void givenExistingUser_whenAuthenticate_thenRetrieveFromDb() {
        try {
            MyUserDto userDTO = new MyUserDto();
            userDTO.setUsername(USERNAME);
            userDTO.setPassword(PASSWORD);

            myUserService.registerNewUserAccount(userDTO);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(USERNAME, PASSWORD);
            Authentication authentication = authenticationProvider.authenticate(auth);

            assertEquals(authentication.getName(), USERNAME);

        } catch (Exception exc) {
            LOG.log(Level.SEVERE, "Error creating account");
        } finally {
            myUserService.removeUserByUsername(USERNAME);
        }
    }

    @Test(expected = BadCredentialsException.class)
    public void givenIncorrectUser_whenAuthenticate_thenBadCredentialsException() {
        try {
            MyUserDto userDTO = new MyUserDto();
            userDTO.setUsername(USERNAME);
            userDTO.setPassword(PASSWORD);

            try {
                myUserService.registerNewUserAccount(userDTO);
            } catch (Exception exc) {
                LOG.log(Level.SEVERE, "Error creating account");
            }

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(USERNAME2, PASSWORD);
            Authentication authentication = authenticationProvider.authenticate(auth);
        } finally {
            myUserService.removeUserByUsername(USERNAME);
        }
    }

}
