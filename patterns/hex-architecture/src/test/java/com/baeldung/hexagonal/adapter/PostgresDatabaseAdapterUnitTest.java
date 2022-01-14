package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.model.User;
import org.junit.Assert;
import org.junit.Test;

public class PostgresDatabaseAdapterUnitTest {

    @Test
    public void getUser_ReturnsUser_ValidUsername() {
        //Arrange
        PostgresDatabaseAdapter postgresDatabaseAdapter = new PostgresDatabaseAdapter();

        //Act
        String username = "test-user";
        User user = postgresDatabaseAdapter.getUser(username);

        //Assert
        String expectedToken = username + " Token fetched from Postgres Db";
        Assert.assertEquals(expectedToken, user.getToken());
    }
}