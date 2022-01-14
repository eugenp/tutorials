package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.port.UserDatabasePort;
import com.baeldung.hexagonal.service.UserService;
import org.junit.Assert;
import org.junit.Test;

public class WebLoginAdapterUnitTest {
    @Test
    public void getLoginToken_ReturnsToken_ValidUsernameAndPassword() {
        //Arrange
        UserDatabasePort mongoDatabase = new MongoDatabaseAdapter();
        UserService mongoService = new UserService(mongoDatabase);
        WebLoginAdapter webLoginAdapter = new WebLoginAdapter(mongoService);
        String username = "test-User";

        //Act
        String token = webLoginAdapter.getLoginToken(username, "password");

        //Assert
        String expectedToken = "Web-Login-Token | " + username + " Token fetched from Mongo Db";
        Assert.assertEquals(expectedToken, token);
    }
}