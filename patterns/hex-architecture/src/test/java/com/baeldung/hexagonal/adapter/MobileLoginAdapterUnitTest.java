package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.port.UserDatabasePort;
import com.baeldung.hexagonal.service.UserService;
import org.junit.Assert;
import org.junit.Test;

public class MobileLoginAdapterUnitTest {

    @Test
    public void getLoginToken_ReturnsToken_ValidUsernameAndPassword() {
        //Arrange
        UserDatabasePort postgresDatabase = new PostgresDatabaseAdapter();
        UserService postgresService = new UserService(postgresDatabase);
        MobileLoginAdapter mobileLoginAdapter = new MobileLoginAdapter(postgresService);
        String username = "test-User";

        //Act
        String token = mobileLoginAdapter.getLoginToken(username, "password");

        //Assert
        String expectedToken = "Mobile-Login-Token | " + username + " Token fetched from Postgres Db";
        Assert.assertEquals(expectedToken, token);
    }
}