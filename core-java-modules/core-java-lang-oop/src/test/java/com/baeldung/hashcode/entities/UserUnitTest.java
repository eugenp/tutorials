package com.baeldung.hashcode.entities;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserUnitTest {

    private User user;
    private User comparisonUser;

    @Before
    public void setUpUserInstances() {
        this.user = new User(1L, "test", "test@domain.com");
        this.comparisonUser = this.user;
    }

    @After
    public void tearDownUserInstances() {
        user = null;
        comparisonUser = null;
    }

    @Test
    public void equals_EqualUserInstance_TrueAssertion() {
        Assert.assertTrue(user.equals(comparisonUser));
    }

    @Test
    public void hashCode_UserHash_TrueAssertion() {
        Assert.assertEquals(1792276941, user.hashCode());
    }
}