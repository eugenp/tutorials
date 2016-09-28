package org.baeldung.userservice;

import org.baeldung.custom.config.MvcConfig;
import org.baeldung.custom.config.PersistenceDerbyJPAConfig;
import org.baeldung.custom.config.SecSecurityConfig;
import org.baeldung.user.service.MyUserService;
import org.baeldung.web.MyUserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { MvcConfig.class, PersistenceDerbyJPAConfig.class, SecSecurityConfig.class })
@WebAppConfiguration
public class CustomUserDetailsServiceTest {

    private static final Logger LOG = Logger.getLogger("CustomUserDetailsServiceTest");

    public static final String USERNAME = "user";
    public static final String PASSWORD = "pass";

    @Autowired
    MyUserService myUserService;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Test
    public void whenAuthenticateUser_thenRetrieveFromDb() {
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

}
