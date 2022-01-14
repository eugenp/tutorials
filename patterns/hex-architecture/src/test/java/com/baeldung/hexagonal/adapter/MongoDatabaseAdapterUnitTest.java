package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.model.User;
import org.junit.Assert;
import org.junit.Test;

public class MongoDatabaseAdapterUnitTest {

    @Test
    public void getUser_ReturnsUser_ValidUsername() {
        //Arrange
        MongoDatabaseAdapter mongoDatabaseAdapter = new MongoDatabaseAdapter();

        //Act
        String username = "test-user";
        User user = mongoDatabaseAdapter.getUser(username);

        //Assert
        String expectedToken = username + " Token fetched from Mongo Db";
        Assert.assertEquals(expectedToken, user.getToken());
    }
}