package com.baeldung.ldap.client;

import com.baeldung.ldap.data.service.UserService;
import com.baeldung.ldap.javaconfig.TestConfig;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("testlive")
@ContextConfiguration(classes = {TestConfig.class}, loader = AnnotationConfigContextLoader.class)
public class LdapDataRepositoryIntegrationTest {

    private static final String USER2 = "TEST02";
    private static final String USER3 = "TEST03";
    private static final String USER4 = "TEST04";

    private static final String USER2_PWD = "TEST02";
    private static final String USER3_PWD = "TEST03";
    private static final String USER4_PWD = "TEST04";

    private static final String SEARCH_STRING = "TEST*";

    @Autowired
    private UserService userService;

    @Test
    public void givenLdapClient_whenCorrectCredentials_thenSuccessfulLogin() {
        Boolean isValid = userService.authenticate(USER3, USER3_PWD);
        assertEquals(true, isValid);
    }

    @Test
    public void givenLdapClient_whenIncorrectCredentials_thenFailedLogin() {
        Boolean isValid = userService.authenticate(USER3, USER2_PWD);
        assertEquals(false, isValid);
    }

    @Test
    public void givenLdapClient_whenCorrectSearchFilter_thenEntriesReturned() {
        List<String> userList = userService.search(SEARCH_STRING);
        assertThat(userList, Matchers.containsInAnyOrder(USER2, USER3));
    }

    @Test
    public void givenLdapClientNotExists_whenDataProvided_thenNewUserCreated() {
        userService.create(USER4, USER4_PWD);
        userService.authenticate(USER4, USER4_PWD);
    }

    @Test
    public void givenLdapClientExists_whenDataProvided_thenExistingUserModified() {
        userService.modify(USER2, USER3_PWD);
        userService.authenticate(USER2, USER3_PWD);
    }

}
