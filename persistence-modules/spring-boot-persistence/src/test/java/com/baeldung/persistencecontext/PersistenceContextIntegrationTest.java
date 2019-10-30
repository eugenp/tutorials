package com.baeldung.persistencecontext;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.baeldung.persistencecontext.PersistenceContextDemoApplication;
import com.baeldung.persistencecontext.entity.User;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersistenceContextDemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class PersistenceContextIntegrationTest {
    @LocalServerPort
    private int port;
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void testThatWhenUserSavedWithTransctionPersistenceContextThenUserShouldGetSavedInDB() {
        User user = new User(121L, "Devender", "admin");
        restTemplate.postForEntity(buildRequestUrl("v1/user/"), user, User.class);
        User userFromTransctionPersistenceContext = restTemplate.getForObject(buildRequestUrl("v1/user/121"), User.class);
        User userFromExtendedPersistenceContext = restTemplate.getForObject(buildRequestUrl("v2/user/121"), User.class);
        assertThat(userFromTransctionPersistenceContext, Is.is(IsNull.notNullValue()));
        assertThat(userFromExtendedPersistenceContext, Is.is(IsNull.notNullValue()));
    }

    @Test(expected = HttpServerErrorException.class)
    public void testThatWhenUserSaveWithOutTransactionInPersistenceContextTypeTransactionThenShouldFailOnPersist() {
        User user = new User(122L, "Devender", "admin");
        restTemplate.postForEntity(buildRequestUrl("v4/user/"), user, User.class);
    }

    @Test
    public void testThatWhenUserSavedWithExtendedPersistenceContextWithoutTransactionThenUserShouldGetBeCached() {
        User user = new User(123L, "Devender", "admin");
        restTemplate.postForEntity(buildRequestUrl("v2/user/"), user, User.class);
        User userFromExtendedPersistenceContext = restTemplate.getForObject(buildRequestUrl("v2/user/123"), User.class);
        User userFromTransctionPersistenceContext = restTemplate.getForObject(buildRequestUrl("v1/user/123"), User.class);
        assertThat(userFromExtendedPersistenceContext, Is.is(IsNull.notNullValue()));
        assertThat(userFromTransctionPersistenceContext, Is.is(IsNull.nullValue()));
    }

    @Test
    public void testThatWhenUserSavedWithExtendedPersistenceContextWithTransactionThenUserShouldFlushCachedEntityIntoDB() {
        User user = new User(124L, "Devender", "admin");
        restTemplate.postForEntity(buildRequestUrl("v2/user/"), user, User.class);
        user = new User(125L, "Devender", "admin");
        restTemplate.postForEntity(buildRequestUrl("v3/user/"), user, User.class);
        User userFromExtendedPersistenceContextuser1 = restTemplate.getForObject(buildRequestUrl("v2/user/124"), User.class);
        User userFromExtendedPersistenceContextuser2 = restTemplate.getForObject(buildRequestUrl("v2/user/125"), User.class);
        User userFromTransctionPersistenceContextuser1 = restTemplate.getForObject(buildRequestUrl("v1/user/124"), User.class);
        User userFromTransctionPersistenceContextuser2 = restTemplate.getForObject(buildRequestUrl("v1/user/125"), User.class);
        assertThat(userFromExtendedPersistenceContextuser1, Is.is(IsNull.notNullValue()));
        assertThat(userFromExtendedPersistenceContextuser2, Is.is(IsNull.notNullValue()));
        assertThat(userFromTransctionPersistenceContextuser1, Is.is(IsNull.notNullValue()));
        assertThat(userFromTransctionPersistenceContextuser2, Is.is(IsNull.notNullValue()));
    }

    private String buildRequestUrl(String path) {
        return "http://localhost:" + port + path;
    }
}
