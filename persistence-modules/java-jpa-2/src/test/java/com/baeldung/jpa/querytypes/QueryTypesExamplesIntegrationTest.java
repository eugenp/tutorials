package com.baeldung.jpa.querytypes;

import org.junit.Assert;
import org.junit.Test;

/**
 * QueryTypesExamples class integration tests.
 *
 * @author Rodolfo Felipe
 */
public class QueryTypesExamplesIntegrationTest {

    QueryTypesExamples userDao = new QueryTypesExamples();

    @Test
    public void givenUserId_whenCallingPlaingQueryMethod_thenReturnExpectedUser() {
        UserEntity firstUser = userDao.getUserByIdWithPlainQuery(1L);
        Assert.assertNotNull("User not found", firstUser);
        Assert.assertEquals("User should be baeldung", "baeldung", firstUser.getName());
        UserEntity lastUser = userDao.getUserByIdWithPlainQuery(4L);
        Assert.assertNotNull("User not found", lastUser);
        Assert.assertEquals("User should be baeldung", "batman", lastUser.getName());
    }

    @Test
    public void givenUserId_whenCallingTypedQueryMethod_thenReturnExpectedUser() {
        UserEntity firstUser = userDao.getUserByIdWithTypedQuery(1L);
        Assert.assertNotNull("User not found", firstUser);
        Assert.assertEquals("User should be baeldung", "baeldung", firstUser.getName());
        UserEntity lastUser = userDao.getUserByIdWithTypedQuery(4L);
        Assert.assertNotNull("User not found", lastUser);
        Assert.assertEquals("User should be baeldung", "batman", lastUser.getName());
    }

    @Test
    public void givenUserId_whenCallingNamedQueryMethod_thenReturnExpectedUser() {
        UserEntity firstUser = userDao.getUserByIdWithNamedQuery(1L);
        Assert.assertNotNull("User not found", firstUser);
        Assert.assertEquals("User should be baeldung", "baeldung", firstUser.getName());
        UserEntity lastUser = userDao.getUserByIdWithNamedQuery(4L);
        Assert.assertNotNull("User not found", lastUser);
        Assert.assertEquals("User should be baeldung", "batman", lastUser.getName());
    }

    @Test
    public void givenUserId_whenCallingNativeQueryMethod_thenReturnExpectedUser() {
        UserEntity firstUser = userDao.getUserByIdWithNativeQuery(1L);
        Assert.assertNotNull("User not found", firstUser);
        Assert.assertEquals("User should be baeldung", "baeldung", firstUser.getName());
        UserEntity lastUser = userDao.getUserByIdWithNativeQuery(4L);
        Assert.assertNotNull("User not found", lastUser);
        Assert.assertEquals("User should be baeldung", "batman", lastUser.getName());
    }

    @Test
    public void givenUserId_whenCallingCriteriaApiMethod_thenReturnExpectedUser() {
        UserEntity firstUser = userDao.getUserByIdWithCriteriaQuery(1L);
        Assert.assertNotNull("User not found", firstUser);
        Assert.assertEquals("User should be baeldung", "baeldung", firstUser.getName());
        UserEntity lastUser = userDao.getUserByIdWithCriteriaQuery(4L);
        Assert.assertNotNull("User not found", lastUser);
        Assert.assertEquals("User should be baeldung", "batman", lastUser.getName());
    }

}
